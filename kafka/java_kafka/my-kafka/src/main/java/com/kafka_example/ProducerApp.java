package com.kafka_example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

import static java.lang.IO.println;

public class ProducerApp {
    public static void main(String[] args) throws IOException {
        var props =new Properties();
        props.load(ProducerApp.class.getClassLoader().getResourceAsStream("producer-config.properties"));
        try( var producer = new KafkaProducer<String,String>(props)){
            int i =0;
           while(true) {
               var event = new UserRegistrationEvent(1, "Feroz", "feroz.shah.940@gmail.com");
                ++i;
               var records = new ProducerRecord<>(
                       "user-registration",
                       1,
                       "user-"+i,
                       event.toString()
               );
               var produceResult =producer.send(records);
               println("Event sent!");
               Thread.sleep(1000);
           }
       }catch (Exception e){
           println(e.getLocalizedMessage());
       }

    }
}
