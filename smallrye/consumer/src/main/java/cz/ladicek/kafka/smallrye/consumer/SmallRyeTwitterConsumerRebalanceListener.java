package cz.ladicek.kafka.smallrye.consumer;

import io.smallrye.reactive.messaging.kafka.KafkaConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Collection;

@ApplicationScoped
@Named("smallrye")
public class SmallRyeTwitterConsumerRebalanceListener implements KafkaConsumerRebalanceListener {
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! gained partitions " + partitions);
    }

    @Override
    public void onPartitionsRevoked(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! lost partitions " + partitions);
    }
}
