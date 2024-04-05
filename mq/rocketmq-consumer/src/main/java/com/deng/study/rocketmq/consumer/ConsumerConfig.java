package com.deng.study.rocketmq.consumer;

import lombok.Data;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.SelectorType;

import java.io.Serializable;

/**
 * @Desc: 消费者的配置信息
 * @Date: 2024/4/5 16:31
 * @Auther: dengyanliang
 */
@Data
public class ConsumerConfig implements Serializable {
    /**
     * 消费者组
     */
    private String consumerGroup;
    /**
     * 订阅主题
     */
    private String topic;
    /**
     * 查询类型
     * SelectorType.TAG根据tag查询，默认是TAG
     * SelectorType.SQL92根据sql查询
     */
    private SelectorType selectorType = SelectorType.TAG;
    /**
     * 订阅子查询条件，默认获取所有
     * 如果selectorType为sql，则值为sql
     */
    private String selectorExpression = "*";
    /**
     * 消息模式，顺序or普通
     */
    private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;
    /**
     * 控制消息模式
     * 如果希望所有订阅者都接收消息全部消息，广播是一个不错的选择。
     * 如果希望一个消费者接收则使用负载均衡模式
     */
    private MessageModel messageModel = MessageModel.CLUSTERING;
    /**
     * 消费者线程数
     */
    private int consumeThreadNumber = 20;
    private long adjustThreadPoolNumsThreshold = 100000;
    /**
     * 重写消费次数
     */
    private int maxReconsumeTimes = -1;
    /**
     * 消费超时时间
     */
    private long consumeTimeout = 15L;

    private int awaitTerminationMillisWhenShutdown = 1000;
    private long suspendCurrentQueueTimeMillis = 1000;

    /**
     * 消费一批消息，最大数
     */
    private int consumeMessageBatchMaxSize = 1;
    /**
     * 拉消息，一次拉多少条
     */
    private int pullBatchSize = 32;

}
