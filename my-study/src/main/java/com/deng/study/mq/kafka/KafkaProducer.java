package com.deng.study.mq.kafka;

import com.alibaba.fastjson.JSON;
import com.deng.study.domain.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<Object,Object> template;

    public void send(String requestMsg){
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setContent(requestMsg);

        template.send("my-test", JSON.toJSONString(message));
    }
}
