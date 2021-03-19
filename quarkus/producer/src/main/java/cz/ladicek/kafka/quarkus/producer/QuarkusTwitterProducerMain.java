package cz.ladicek.kafka.quarkus.producer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusTwitterProducerMain {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
