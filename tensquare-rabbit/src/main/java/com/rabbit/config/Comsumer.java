package com.rabbit.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class Comsumer {

  @RabbitHandler
  public void showMessage(String message){
    System.out.println("ITcast接受到的信息："+message);
  }


}
