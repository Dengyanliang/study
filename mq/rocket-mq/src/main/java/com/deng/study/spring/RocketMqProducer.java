package com.deng.study.spring;

import com.alibaba.fastjson.JSON;
import com.deng.study.enums.DelayTimeLevelEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @Desc:
 * @Date: 2024/2/4 12:46
 * @Auther: dengyanliang
 */

@Component
public class RocketMqProducer {

    private final Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);

    private DefaultMQProducer producer;

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.group}")
    private String groupName;

    /**
     * mq 初始化
     */
    @PostConstruct
    private void producerInit(){
        logger.info("RocketMqProducer 开始启动.....");
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);

        try {
            producer.start();
            logger.info("RocketMqProducer 启动成功.....");
        } catch (MQClientException e) {
            logger.error("RocketMqProducer 启动出现异常....",e);
        }
    }

    public void sendAysnc(String topic, String tag, Object obj) {
        sendAysnc(topic,tag,obj, DelayTimeLevelEnum.DELAY_0s);
    }

    public void sendAysnc(String topic, String tag, Object obj, DelayTimeLevelEnum delayTimeLevel) {
        Message message = new Message(topic,tag, JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8));
        if(Objects.nonNull(delayTimeLevel)){
            message.setDelayTimeLevel(delayTimeLevel.getLevel());
        }

        String key = UUID.randomUUID().toString().replace("-","");
        try {
            if(StringUtils.isNotBlank(key)){
                message.setKeys(key);
            }
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("发送异步消息成功,{}",JSON.toJSONString(sendResult));
                }

                @Override
                public void onException(Throwable e) {
                    logger.error("发送异步消息异常，msg：{},异常信息",JSON.toJSONString(message),e);
                }
            });
        } catch (Exception e) {
            logger.error("发送异步消息异常，msg：{},异常信息",JSON.toJSONString(message),e);
        }

    }
}
