

# Run consumer group1- Email event
```shell
mvn exec:java -Dexec.mainClass="com.kafka_example.ConsumerApp" -Dexec.args="email-event"
```

# Run consumer group2- Payment event
```shell
mvn exec:java -Dexec.mainClass="com.kafka_example.ConsumerApp" -Dexec.args="payment-event"
```

# Run consumer group3- User event
```shell
mvn exec:java -Dexec.mainClass="com.kafka_example.ConsumerApp" -Dexec.args="user-service"
```