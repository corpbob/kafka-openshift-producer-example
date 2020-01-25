# Create a kafka topic called my-topic. Change the name to suite your requirements. 

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


- Put the file kafka.keystore.jks in the same directory as when you launch the application
