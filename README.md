# First, Download kafka and unpack to $KAFKA_HOME which you will define.
# Start Kafka

```
cd $KAFKA_HOME
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

# Create topic

```
bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 2 \
    --topic test-topic
```

# Start Subscriber

```
${KAFKA_HOME}/bin/kafka-console-consumer.sh --topic test-topic --bootstrap-server localhost:9092
```

# Local Development

- Put the file kafka.keystore.jks in the same directory as when you launch the application

- Launch the application in DEV mode

```
KAFKA_TOPIC=test-topic KAFKA_BOOTSTRAP_SERVERS=localhost:9092 TRUST_STORE=kafka.keystore.jks TRUST_STORE_PASSWORD=changeme mvn compile quarkus:dev
```

- Test the application

```
curl -v -H "Content-Type: applicatilocalhost:8080 -d '{ "number": "0" }'
```

 
# Openshift Deployment (Under construction)

Create a kafka topic called my-topic. Change the name to suite your requirements. 

cat <<EOF|oc create -f -
apiVersion: kafka.strimzi.io/v1alpha1
kind: KafkaTopic
metadata:
 name: my-topic
 labels:
   strimzi.io/cluster: "my-cluster"
spec:
 partitions: 3
 replicas: 3
EOF

- To list the topics:

oc get KafkaTopic -n <namespace>


