package com.deng.study.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(id = "webGroup",topics = "kafka-test-topic")
    public void receive(String message){
      log.info("接收到消息：{}",message);
    }

}
