package com.kafka_example;

public record UserRegistrationEvent(int id , String name , String email) {
    @Override
    public String toString(){
        return id+","+name+","+email;
    }
}
