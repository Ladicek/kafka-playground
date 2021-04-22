package cz.ladicek.kafka.quarkus.avro.producer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusAvroTwitterProducerMain {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
