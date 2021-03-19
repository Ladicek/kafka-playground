package cz.ladicek.kafka.smallrye.producer;

import cz.ladicek.kafka.TwitterAuth;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SmallRyeTwitterProducer {
    @Outgoing("tweets-producer")
    public Multi<Message<String>> produce() {
        return Multi.createFrom().emitter(em -> {
            Configuration config = new ConfigurationBuilder()
                    .setOAuthConsumerKey(TwitterAuth.CONSUMER_KEY)
                    .setOAuthConsumerSecret(TwitterAuth.CONSUMER_SECRET)
                    .setOAuthAccessToken(TwitterAuth.ACCESS_TOKEN)
                    .setOAuthAccessTokenSecret(TwitterAuth.ACCESS_TOKEN_SECRET)
                    .build();
            TwitterStream twitter = new TwitterStreamFactory(config).getInstance();
            twitter.addListener(new StatusAdapter() {
                @Override
                public void onStatus(Status status) {
                    System.out.println("[" + Thread.currentThread().getName() + "] " + status.getId() + " " + status.getText());

                    em.emit(KafkaRecord.of("" + status.getId(), status.getText()));
                }
            });
            twitter.sample("en");
        });
    }
}
