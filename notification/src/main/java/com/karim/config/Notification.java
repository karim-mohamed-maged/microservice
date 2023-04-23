package com.karim.config;

import com.karim.model.Contants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Notification {

    @RabbitListener(queues = Contants.QUEUE)
    public void consumeMessageFromQueue(String message){
        System.out.println(message);
    }
}
