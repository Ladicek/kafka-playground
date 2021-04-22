package cz.ladicek.kafka.quarkus.avro.consumer;

import cz.ladicek.kafka.quarkus.avro.Tweet;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class QuarkusAvroTwitterConsumer {
    @Incoming("tweets-consumer")
    public CompletionStage<Void> consume(KafkaRecord<Long, Tweet> message) {
        System.out.println("consumed message from partition " + message.getPartition() + ": " + message.getPayload().getText());
        return message.ack();
    }

//    @Incoming("tweets-consumer")
//    public CompletionStage<Void> consume(KafkaRecord<Long, GenericRecord> message) {
//        System.out.println("consumed message from partition " + message.getPartition() + ": " + message.getPayload().get("text"));
//        return message.ack();
//    }
}
