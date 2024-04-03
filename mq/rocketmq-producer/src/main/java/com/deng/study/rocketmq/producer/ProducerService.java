package com.deng.study.rocketmq.producer;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Desc:
 * @Date: 2024/4/3 20:37
 * @Auther: dengyanliang
 */
@Slf4j
@Data
public class ProducerService {

    private SimpleProducer simpleProducer;

    /**
     * 异步发送消息
     */
    public <T> void sendMessage(String topic, T t) {
        this.sendMessage(topic, null, t, null, new DefaultSendCallback());
    }

    /**
     * 异步发送消息
     */
    public <T> void sendMessage(String topic, T t, SendCallback sendCallback) {
        this.sendMessage(topic, null, t, null, sendCallback);
    }

    /**
     * 异步发送消息
     */
    public <T> void sendMessage(String topic, String tag, T t) {
        this.sendMessage(topic, tag, t, null, new DefaultSendCallback());
    }

    /**
     * 异步发送消息
     */
    public <T> void sendMessage(String topic, String tag, T t, SendCallback sendCallback) {
        this.sendMessage(topic, tag, t, null, sendCallback);
    }

    /**
     * 异步发送延时消息
     */
    public <T> void sendMessage(String topic, String tag, T t, Long delayTimeSec) {
        this.sendMessage(topic, tag, t, delayTimeSec, new DefaultSendCallback());
    }

    /**
     * 异步发送消息
     */
    public <T> void sendMessage(String topic, String tag, T t, Long delayTimeSec, SendCallback sendCallback){
        String body;
        if(t instanceof String){
            body = (String) t;
        }else{
            body = JSON.toJSONString(t);
        }
        Message message = this.constructMessage(topic,tag,body);
        if(Objects.nonNull(delayTimeSec)){
            message.setDelayTimeSec(delayTimeSec);
        }
        message.setKeys(IdUtil.fastSimpleUUID());
        try{
            this.sendMessage(message, sendCallback);
        }catch(Exception e){
            log.error("发送消息失败：topic:{},tag:{}, data:{},异常信息：{}", topic, tag, body, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 异步发送消息
     */
    private void sendMessage(Message message, SendCallback sendCallback){
        try{
            simpleProducer.getProducer().send(message,sendCallback);
        }catch(Exception e){
            log.error("发送消息失败：message:{}, 异常信息：{}", message.getProperties(), e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 同步发送消息
     */
    public <T> void sendSyncMessage(String topic, T t) {
        this.sendSyncMessage(topic, null, t, null);
    }

    /**
     * 同步发送消息
     */
    public <T> void sendSyncMessage(String topic, String tag, T t) {
        this.sendSyncMessage(topic, tag, t, null);
    }

    /**
     * 同步发送消息
     */
    public <T> SendResult sendSyncMessage(String topic, String tag, T t, Long delayTimeSec) {
        String body;
        if (t instanceof String) {
            body = (String) t;
        } else {
            body = JSON.toJSONString(t);
        }
        Message message = this.constructMessage(topic, tag, body);
        if (null != delayTimeSec) {
            message.setDelayTimeSec(delayTimeSec);
        }
        message.setKeys(IdUtil.fastSimpleUUID()); // 自定义消息的key
        try {
            return this.sendSyncMessage(message);
        } catch (Exception e) {
            log.error("发送消息失败：topic:{},tag:{}, data:{},异常信息：{}", topic, tag, body, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 同步发送消息
     */
    private SendResult sendSyncMessage(Message message) {
        try {
            return simpleProducer.getProducer().send(message);
        } catch (Exception e) {
            log.error("发送消息失败：message:{}, 异常信息：{}", message.getProperties(), e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }


    public Message constructMessage(String topic, String tag, String body) {
        return this.constructMessage(topic, tag, body.getBytes(StandardCharsets.UTF_8));
    }

    public Message constructMessage(String topic, String tag, byte[] body) {
        if (StringUtils.isBlank(tag)) {
            return new Message(topic, body);
        }
        return new Message(topic, tag, body);
    }
}
