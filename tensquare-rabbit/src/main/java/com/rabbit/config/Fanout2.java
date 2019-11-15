package com.rabbit.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "kudingyu")
public class Fanout2 {

  @RabbitHandler
  public void showMessage(String message){
    System.out.println("KuDingYu:  "+message);
  }


}
