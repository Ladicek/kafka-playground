package cz.ladicek.kafka.vertx;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import cz.ladicek.kafka.TwitterAuth;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class VertxTwitterProducer {
    public static void main(String[] args) {
        OAuth10aService service = new ServiceBuilder(TwitterAuth.CONSUMER_KEY)
                .apiSecret(TwitterAuth.CONSUMER_SECRET)
                .build(TwitterApi.instance());
        OAuth1AccessToken accessToken = new OAuth1AccessToken(TwitterAuth.ACCESS_TOKEN, TwitterAuth.ACCESS_TOKEN_SECRET);
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://stream.twitter.com/1.1/statuses/sample.json");
        request.addQuerystringParameter("language", "en");
        service.signRequest(accessToken, request);

        Vertx vertx = Vertx.vertx();

        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = KafkaProducer.create(vertx, config);

        JsonParser parser = JsonParser.newParser().objectValueMode();
        parser.handler(event -> {
            JsonObject object = event.objectValue();
            String id = object.getString("id_str");
            String text = object.getString("text");
            System.out.println("[" + Thread.currentThread().getName() + "] " + id + " " + text);
            producer.write(KafkaProducerRecord.create("tweets", id, text));
        });

        WebClient client = WebClient.create(vertx);
        client.getAbs(request.getCompleteUrl())
                .putHeader("Authorization", request.getHeaders().get("Authorization"))
                .as(BodyCodec.jsonStream(parser))
                .send();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            client.close();
            producer.close()
                    .compose(ignored -> vertx.close())
                    .toCompletionStage().toCompletableFuture().join();
        }));
    }
}
