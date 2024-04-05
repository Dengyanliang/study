package com.deng.study.rocketmq.consumer;

import com.deng.study.rocketmq.common.RocketMqProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.spring.annotation.SelectorType;

/**
 * @Desc: 创建消费者
 * @Date: 2024/4/5 16:04
 * @Auther: dengyanliang
 */
@Slf4j
@Data
public class SimpleConsumer {
    private DefaultMQPushConsumer consumer;
    // rocketMq的配置
    private RocketMqProperties rocketMqProperties;
    // 消费者的配置
    private ConsumerConfig consumerConfig;
    // 消息监听器
    private MessageListener messageListener;

    public SimpleConsumer(RocketMqProperties rocketMqProperties, ConsumerConfig consumerConfig) {
        this.rocketMqProperties = rocketMqProperties;
        this.consumerConfig = consumerConfig;
    }

    public void init() throws MQClientException {
        RPCHook aclClientRPCHook = new AclClientRPCHook(new SessionCredentials(rocketMqProperties.getAccessKey(), rocketMqProperties.getSecretKey()));
        consumer = new DefaultMQPushConsumer(null,consumerConfig.getConsumerGroup(),aclClientRPCHook);
        consumer.setNamesrvAddr(rocketMqProperties.getNameServer());
        consumer.setVipChannelEnabled(false);
        consumer.setMessageModel(consumerConfig.getMessageModel());
        //订阅主题
        if (consumerConfig.getSelectorType() == SelectorType.TAG) {
            //如果是根据tag过滤消息
            consumer.subscribe(consumerConfig.getTopic(), consumerConfig.getSelectorExpression());
        } else if (consumerConfig.getSelectorType() == SelectorType.SQL92) {
            //如果是根据sql过滤消息
            consumer.subscribe(consumerConfig.getTopic(), MessageSelector.bySql(consumerConfig.getSelectorExpression()));
        }
        //设置消费者线程数
        consumer.setConsumeThreadMin(consumerConfig.getConsumeThreadNumber());
        consumer.setConsumeThreadMax(consumerConfig.getConsumeThreadNumber());
        consumer.setAdjustThreadPoolNumsThreshold(consumerConfig.getAdjustThreadPoolNumsThreshold());
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(consumerConfig.getMaxReconsumeTimes());
        //消费超时时间
        consumer.setConsumeTimeout(consumerConfig.getConsumeTimeout());
        //等待停服时间
        consumer.setAwaitTerminationMillisWhenShutdown(consumerConfig.getAwaitTerminationMillisWhenShutdown());
        consumer.setSuspendCurrentQueueTimeMillis(consumerConfig.getSuspendCurrentQueueTimeMillis());
        //每次拉取消息数
        consumer.setConsumeMessageBatchMaxSize(consumerConfig.getConsumeMessageBatchMaxSize());
        consumer.setPullBatchSize(consumerConfig.getPullBatchSize());

        //设置监听器
        consumer.registerMessageListener(messageListener);
        consumer.start();
    }

    public void destory(){
        log.info(">>>>>>> {} rocketmq consumer shutdown",this.messageListener.getClass());
        consumer.shutdown();
    }
}

