package cz.ladicek.kafka.quarkus.consumer;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class QuarkusTwitterConsumer {
    @Incoming("tweets-consumer")
    public CompletionStage<Void> consume(KafkaRecord<String, String> message) {
        System.out.println("consumed message from partition " + message.getPartition());
        return message.ack();
    }
}
