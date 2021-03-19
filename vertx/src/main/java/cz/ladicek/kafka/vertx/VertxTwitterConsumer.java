package cz.ladicek.kafka.vertx;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class VertxTwitterConsumer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Map<String, String> config = new HashMap<>();
        config.put("bootstrap.servers", "localhost:9092");
        config.put("group.id", "vertx");
        config.put("enable.auto.commit", "false");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, config);

        consumer.partitionsRevokedHandler(partitions -> System.out.println("!!! lost partitions " + partitions));
        consumer.partitionsAssignedHandler(partitions -> System.out.println("!!! gained partitions " + partitions));
        consumer.subscribe("tweets");

        pollLoop(consumer)
                .compose(ignored -> consumer.close())
                .compose(ignored -> vertx.close())
                .toCompletionStage().toCompletableFuture().join();
    }

    private static Future<Void> pollLoop(KafkaConsumer<String, String> consumer) {
        Promise<Void> completer = Promise.promise();
        AtomicBoolean stop = new AtomicBoolean(false);
        consumer.poll(Duration.ofMillis(5000))
                .compose(it -> {
                    ConsumerRecords<String, String> records = it.records();

                    System.out.println("!!! consumed " + records.count() + " records from partitions " + records.partitions());

                    if (records.isEmpty()) {
                        System.out.println("!!! empty");
                        stop.set(true);
                    }

                    return consumer.commit();
                })
                .onSuccess(ignored -> {
                    if (!stop.get()) {
                        propagateCompletion(pollLoop(consumer), completer);
                    } else {
                        completer.complete();
                    }
                });
        return completer.future();
    }

    private static <T> void propagateCompletion(Future<T> from, Promise<T> to) {
        from.onComplete(event -> {
            if (event.succeeded()) {
                to.complete(event.result());
            } else {
                to.fail(event.cause());
            }
        });
    }
}
