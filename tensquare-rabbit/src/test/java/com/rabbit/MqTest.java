package com.rabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class MqTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  // 直接模式
  @Test
  public void testSend() {
    System.out.println("=========================");
    rabbitTemplate.convertAndSend("itcast", "我要红包");
    System.out.println("--------------------------");
  }


  // 散列模式
  @Test
  public void testSendFanout() {
    rabbitTemplate.convertAndSend("chuanzhi", "", "分列模式走起");
  }


// topic
  @Test
  public void testSendTopic(){
    rabbitTemplate.convertAndSend("topictest","goods.aaa","主题模式");
    rabbitTemplate.convertAndSend("topictest","article.content.log","主题模式");
    rabbitTemplate.convertAndSend("topictest","goods.log","主题模式");
  }

}
