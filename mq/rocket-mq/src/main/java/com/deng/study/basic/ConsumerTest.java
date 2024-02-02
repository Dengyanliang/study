package com.deng.study.basic;

import com.deng.study.enums.DelayTimeLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Date: 2024/2/2 14:11
 * @Auther: dengyanliang
 */
@Slf4j
public class ConsumerTest {
    public static void main(String[] args) {
        try {
            // 定义pull消费者
//            DefaultLitePullConsumer pullConsumer = new DefaultLitePullConsumer("cg");

            // 定义push消费者，必须指定消费者组，否则会报错，因为生产者也是往cg组中发送消息的
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
            consumer.setNamesrvAddr("localhost:9876");
            // 定义从哪里开始消费，这里指定从第一条消息开始消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            // 指定消费的topic和tag
            consumer.subscribe("topic_test_ack","*");
            // 采用"广播模式"进行消费，默认为"集群模式"
            consumer.setMessageModel(MessageModel.BROADCASTING);

            // 注册消息监听器
            consumer.registerMessageListener(new MessageListenerConcurrently(){
                // 一旦broker中有了其订阅的消息就会触发该方法的执行
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    List<RetrySend> list = new ArrayList<>();
                    // 逐条消费消息
                    try{
                        for (MessageExt msg : msgs) {
                            int delayTimeLevel = msg.getDelayTimeLevel();

                            String body = new String(msg.getBody());
                            String substring = body.substring(0, body.lastIndexOf("_"));
                            int count = Integer.parseInt(body.substring(body.lastIndexOf("_")+1));

                            System.out.println("原始字符串："+body + "，截取后：" + substring + "，次数：" + count + "，延时等级：" + delayTimeLevel);

//                        log.info("原始字符串：{},截取后：{}，次数：{}，延时等级：{}", body, substring, count, delayTimeLevel);
                            if(count < 3){ // 小于3次进行重试
                                RetrySend retrySend = new RetrySend(substring,count,delayTimeLevel);
                                list.add(retrySend);
                            }else{  // 多余3次进行持久化
                                save(substring,count,delayTimeLevel);
                            }
                        }
                        // 返回消费状态，这里是进行ACK确认
                        System.out.println("--------3333---------");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                    }catch(Exception e){
                        // 稍后消费
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }finally{
                        if(CollectionUtils.isNotEmpty(list)){
                            System.out.println("---------111--------");
                            retrySend(list);
                            System.out.println("---------222--------");
                        }
                    }
                }
            });

            consumer.start();
            System.out.println("consumer start....");

        } catch (Exception e) {
            // 延时消费
//            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    private static void retrySend(List<RetrySend> retrySendList){
        for (RetrySend item : retrySendList) {
            retrySend(item.getStr(),item.getCount(),item.getDelayTimeLevel());
        }
    }

    /**
     * 发送到一定次数，进行持久化
     */
    private static void save(String substring,int count,int delayTimeLevel){
        System.out.println("进行持久化，substring：" + substring + "，次数：" + count + "，延时等级：" + delayTimeLevel);
    }

    /**
     * 重新发送
     */
    private static void retrySend(String subStr,int count,int delayTimeLevel){
        try {
            DefaultMQProducer producer = new DefaultMQProducer("pg");
            producer.setNamesrvAddr("localhost:9876");
            // 指定异步发送失败后进行重试的次数
            producer.setRetryTimesWhenSendAsyncFailed(3);
            // 指定新创建的topic的Queue数量为3，默认为4，也就是queueId的值
            producer.setDefaultTopicQueueNums(3);

            producer.start();

            String str = subStr + "_" + (count + 1);
            System.out.println("重新发送：" + str);
//            log.info("重新发送：{}", str);

            byte[] body = str.getBytes();
            Message message = new Message("topic_test_ack","asycSend",body);
            message.setDelayTimeLevel(DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel + 1).getCode());

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("重新发送的延时等级："+message.getDelayTimeLevel());
//                    log.info("重新发送的延时等级：{}", message.getDelayTimeLevel());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println(e+"");
//                    log.info(e+"");
                }
            });

            // 睡眠3秒。这个休眠很重要，否则消费没有发送，执行shutdown会报connect to null failed
            TimeUnit.SECONDS.sleep(3);
            producer.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RetrySend{
    private String str;
    private int count;
    private int delayTimeLevel;
}


