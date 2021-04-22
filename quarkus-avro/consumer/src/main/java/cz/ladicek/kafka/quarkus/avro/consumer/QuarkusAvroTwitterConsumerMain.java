package cz.ladicek.kafka.quarkus.avro.consumer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusAvroTwitterConsumerMain {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
