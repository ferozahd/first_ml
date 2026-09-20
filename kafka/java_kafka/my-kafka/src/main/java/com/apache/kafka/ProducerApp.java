package com.apache.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
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
                    
                       "user-"+i,
                       event.toString()
               );
               long start =System.currentTimeMillis();
               var produceResult =producer.send(records,((metadata, exception) -> {
                   if(exception !=null){
                       println("FAILED: "+exception.getLocalizedMessage());
                   }else{
                       println("Success");
                   }
               }));
               println("Event sent!");
               long time =System.currentTimeMillis()-start;
               println("Consume time "+time);
               Thread.sleep(1000);
           }
       }catch (Exception e){
           println(e.getLocalizedMessage());
       }

    }
}
