package cz.ladicek.kafka.simple;

import cz.ladicek.kafka.TwitterAuth;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Properties;

public class SimpleTwitterProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

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

                producer.send(new ProducerRecord<>("tweets", "" + status.getId(), status.getText()));
            }
        });
        twitter.sample("en");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitter.shutdown();

            producer.close();
        }));
    }
}
