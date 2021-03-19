package cz.ladicek.kafka.smallrye.consumer;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class SmallRyeTwitterConsumer {
    @Incoming("tweets-consumer")
    public CompletionStage<Void> consume(KafkaRecord<String, String> message) {
        System.out.println("consumed message from partition " + message.getPartition());
        return message.ack();
    }
}
