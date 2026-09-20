package com.apache.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static java.lang.IO.println;

public class ConsumerFlinkApp {
    static void main(String[] args) {

        var props=getPropsInputStream();
        props.setProperty("group.id","primary-consumer");
        try( var consumer = new KafkaConsumer<String,String>(props)){
            consumer.subscribe(List.of("output-events"));
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


    public static Properties getPropsInputStream(){
        try(var propsInputStream = ConsumerFlinkApp.class
                .getClassLoader()
                .getResourceAsStream("consumer-config.properties")){
            var props =new Properties();
            props.load(propsInputStream);
            return props;
        }catch (IOException ex){
            throw new RuntimeException("Our system is not able to get props :"+ex.getLocalizedMessage());
        }
    }


}
