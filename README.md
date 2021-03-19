# Kafka playground

This is my Kafka playground.

No guarantees that anything will be useful, but feel free to look around :-)

## Start Kafka locally

To run a playground single-node Kafka cluster locally, run:

```bash
wget https://downloads.apache.org/kafka/2.7.0/kafka_2.13-2.7.0.tgz
tar xfzv kafka_2.13-2.7.0.tgz
cd kafka_2.13-2.7.0/

./bin/zookeeper-server-start.sh config/zookeeper.properties

./bin/kafka-server-start.sh config/server.properties

./bin/kafka-topics.sh --create --topic tweets --partitions 10 --bootstrap-server localhost:9092
./bin/kafka-topics.sh --describe --topic tweets --bootstrap-server localhost:9092
```

## Twitter auth

The examples here use Twitter as a source of messages that are produced into Kafka.
You have to update the `TwitterAuth` class first, otherwise nothing will work.
