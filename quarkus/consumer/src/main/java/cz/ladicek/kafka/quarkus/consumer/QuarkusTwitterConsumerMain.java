package cz.ladicek.kafka.quarkus.consumer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusTwitterConsumerMain {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
