package com.rabbit.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itheima")
public class Fanout {

  @RabbitHandler
  public void showMessage(String message){
    System.out.println("ItHeiMa: "+message);
  }


}
