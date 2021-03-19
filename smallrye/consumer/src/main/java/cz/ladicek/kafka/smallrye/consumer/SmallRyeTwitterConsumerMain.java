package cz.ladicek.kafka.smallrye.consumer;

import javax.enterprise.inject.se.SeContainerInitializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.logging.LogManager;

public class SmallRyeTwitterConsumerMain {
    static {
        try {
            InputStream is = SmallRyeTwitterConsumerMain.class.getResourceAsStream("/logging.properties");
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        SeContainerInitializer.newInstance().initialize();
    }
}
