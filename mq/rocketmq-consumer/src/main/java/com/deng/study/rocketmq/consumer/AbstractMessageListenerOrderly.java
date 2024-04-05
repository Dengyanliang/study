package com.deng.study.rocketmq.consumer;

import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Desc: 顺序消费
 * @Date: 2024/4/5 18:05
 * @Auther: dengyanliang
 */
@Slf4j
public abstract class AbstractMessageListenerOrderly<T> extends AbstractBaseMessageHander<T> implements MessageListenerOrderly {


    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        for (MessageExt msg : msgs) {
            log.debug("received msg: {}", msg);
            try{
                super.handleMessage(msg);
            }catch(Exception e){
                log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", msg.getMsgId(), msg.getTopic(), msg.getReconsumeTimes(), e);
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }

}
