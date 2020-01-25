package com.example.kafka;

import java.util.Properties;

import javax.enterprise.event.Observes;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.JsonObject;

@Path("/")
public class KafkaClient {

	private Producer<String, String> producer;
	private String topic = System.getenv("KAFKA_TOPIC");
	private String trustStore = System.getenv("TRUST_STORE");
	private String trustStorePassword = System.getenv("TRUST_STORE_PASSWORD");
	private String kafkaBootStrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public JsonObject publish(String jsonString) {
		JsonObject obj = new JsonObject(jsonString);
		System.out.println("object = " + obj);
		producer.send(new ProducerRecord<String, String>(topic, obj.toString()));

		JsonObject response = new JsonObject();
		response.put("message", "OK");
		return response;
	}

	public void init(@Observes StartupEvent ev) {

		if (null == topic || "".equals(topic)) {
			topic = "default-topic";
		}

		if (null == trustStore || "".equals(trustStore)) {
			throw new RuntimeException("No truststore defined");
		}

		if (null == trustStorePassword || "".equals(trustStorePassword)) {
			throw new RuntimeException("No truststore password defined");
		}
		
		if (null == kafkaBootStrapServers || "".equals(kafkaBootStrapServers)) {
			throw new RuntimeException("No kafka bootstrap servers");
		}
		
		

		Properties props = new Properties();

		props.put("bootstrap.servers", kafkaBootStrapServers);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("ssl.truststore.location", trustStore);
		props.put("ssl.truststore.password", trustStorePassword);

		producer = new KafkaProducer<String, String>(props);
	}

}
