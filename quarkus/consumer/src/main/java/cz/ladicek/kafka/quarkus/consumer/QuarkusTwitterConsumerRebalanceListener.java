package cz.ladicek.kafka.quarkus.consumer;

import io.smallrye.reactive.messaging.kafka.KafkaConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Collection;

@ApplicationScoped
@Named("quarkus")
public class QuarkusTwitterConsumerRebalanceListener implements KafkaConsumerRebalanceListener {
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! gained partitions " + partitions);
    }

    @Override
    public void onPartitionsRevoked(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! lost partitions " + partitions);
    }
}
