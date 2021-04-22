package cz.ladicek.kafka.quarkus.avro.producer;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(classNames = {"twitter4j.TwitterImpl", "twitter4j.DispatcherImpl", "twitter4j.HttpClientImpl"})
public class Twitter4jReflection {
}
