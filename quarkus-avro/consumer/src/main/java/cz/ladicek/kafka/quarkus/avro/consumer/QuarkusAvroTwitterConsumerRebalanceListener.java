package cz.ladicek.kafka.quarkus.avro.consumer;

import io.smallrye.reactive.messaging.kafka.KafkaConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Collection;

@ApplicationScoped
@Named("quarkus-avro")
public class QuarkusAvroTwitterConsumerRebalanceListener implements KafkaConsumerRebalanceListener {
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! gained partitions " + partitions);
    }

    @Override
    public void onPartitionsRevoked(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        System.out.println("!!! lost partitions " + partitions);
    }
}
