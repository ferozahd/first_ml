package com.kafka_example;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static java.lang.IO.println;

public class ConsumerApp {
    public static void main(String[] args) throws IOException {
        var props =new Properties();
        props.load(ConsumerApp.class.getClassLoader().getResourceAsStream("consumer-config.properties"));
        props.setProperty("group.id",args[0]);
        try( var consumer = new KafkaConsumer<String,String>(props)){
            consumer.subscribe(List.of("user-registration"));
            while (true){
                var records= consumer.poll(Duration.ofMillis(1000));
                for(var record : records){
                    println("Received: "+ record.value()+" Partition: "+ record.partition()+" Offset: "+record.offset());
                }
            }
        }catch (Exception e){
            println(e.getLocalizedMessage());
        }
    }
}
