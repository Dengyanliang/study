package com.deng.study.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Desc: 并发消费
 * @Date: 2024/4/5 17:56
 * @Auther: dengyanliang
 */
@Slf4j
public abstract class AbstractMessageListenerConcurrently<T> extends AbstractBaseMessageHander<T> implements MessageListenerConcurrently {

    /**
     * 消费消息
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs) {
            try{
                super.handleMessage(msg);
            }catch(Exception e){
                log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", msg.getMsgId(), msg.getTopic(), msg.getReconsumeTimes(), e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
