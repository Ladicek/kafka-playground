package cz.ladicek.kafka.smallrye.producer;

import javax.enterprise.inject.se.SeContainerInitializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.logging.LogManager;

public class SmallRyeTwitterProducerMain {
    static {
        try {
            InputStream is = SmallRyeTwitterProducerMain.class.getResourceAsStream("/logging.properties");
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        SeContainerInitializer.newInstance().initialize();
    }
}
