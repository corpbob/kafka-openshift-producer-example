FROM centos:latest
RUN yum install -y java-1.8.0-openjdk-devel
RUN adduser kafka
RUN chmod -R a+rwx /home/kafka
USER kafka
ADD kafka-openshift-producer-example-0.1-jar-with-dependencies.jar /home/kafka
ADD kafka.keystore.jks /home/kafka
ENTRYPOINT cd /home/kafka && java -jar kafka-openshift-consumer-example-0.1-jar-with-dependencies.jar
