package com.deng.study.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @Desc: 消息回调
 * @Date: 2024/4/3 20:39
 * @Auther: dengyanliang
 */
@Slf4j
public class DefaultSendCallback implements SendCallback {

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info(">>>>>消息发送成功,msgId:{},transactionId:{},topic:{}",sendResult.getMsgId(),
                sendResult.getTransactionId(),
                sendResult.getMessageQueue().getTopic());
    }

    @Override
    public void onException(Throwable throwable) {
        log.error(">>>>>消息发送异常",throwable);
    }
}
