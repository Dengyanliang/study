package com.deng.study.mq.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumerListener {

    @JmsListener(destination = "${spring.activemq.topic-name}",containerFactory = "topicListener")
    public void readActiveQueue(String message){
        System.out.println("topic接收到消息：" + message);
    }
}
