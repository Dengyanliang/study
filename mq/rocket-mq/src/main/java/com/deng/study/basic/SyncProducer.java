package com.deng.study.basic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @Desc:
 * @Date: 2024/2/2 12:05
 * @Auther: dengyanliang
 */
public class SyncProducer {
    public static void main(String[] args) {
        try {
            // 创建一个producer，参数为pg的producerGroup
            DefaultMQProducer producer = new DefaultMQProducer("pg");
            // 指定nameServer地址
            producer.setNamesrvAddr("localhost:9876");
            // 当同步发送失败时设置的重试次数
            producer.setRetryTimesWhenSendFailed(3);
            // 设置发送超时时限为5s，默认为3s
            producer.setSendMsgTimeout(5000);
            // 开启生产者
            producer.start();

            // 生产并发送10条数据
            for (int i = 0; i < 10; i++) {
                byte[] body = ("hi," + i).getBytes();
                Message message = new Message("topic_test","someTag",body);
                // 设置延迟等级，设置完之后，消费者可以在等级对应的时间后才能接收到消息并消费
                message.setDelayTimeLevel(4);
                message.setKeys("key-" + i);
                // 同步发送数据
                SendResult sendResult = producer.send(message);
                //public enum SendStatus {
                //    SEND_OK, 发送成功
                //    FLUSH_DISK_TIMEOUT, 刷盘超时。当broker设置为同步刷盘策略时才可能出现；异步刷盘不会出现
                //    FLUSH_SLAVE_TIMEOUT, slave同步超时。当broker集群设置的master-slave的复制方式为同步复制时才有可能出现这种异常；异步复制不会
                //    SLAVE_NOT_AVAILABLE, 没有可用的slave。当broker集群设置的master-slave的复制方式为同步复制时才有可能出现这种异常；异步复制不会
                //}
                // sendStatus=SEND_OK，
                System.out.println(sendResult);
            }
            producer.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
