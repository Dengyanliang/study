package com.deng.study.basic;

import com.alibaba.fastjson.JSON;
import com.deng.study.dto.SendMsgDTO;
import com.deng.study.enums.DelayTimeLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
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
            // 定义从哪里开始消费，这里指定从第一条消息开始消费，默认为CONSUME_FROM_LAST_OFFSET
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            // 指定消费的topic和tag
            consumer.subscribe("topic_test_ack","asycSend");
            // 采用"广播模式"进行消费，默认为"集群模式"
            consumer.setMessageModel(MessageModel.BROADCASTING);

            // 注册消息监听器
            consumer.registerMessageListener(new MessageListenerConcurrently(){
                // 一旦broker中有了其订阅的消息就会触发该方法的执行
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    List<SendMsgDTO> list = new ArrayList<>();
                    // 逐条消费消息
                    try{
                        for (MessageExt msg : msgs) {
                            int delayTimeLevel = msg.getDelayTimeLevel();

                            String body = new String(msg.getBody());
//                            String payNo = body.substring(0, body.lastIndexOf("_"));
//                            int count = Integer.parseInt(body.substring(body.lastIndexOf("_")+1));
//                            System.out.println("原始字符串："+body + "，截取后：" + payNo + "，次数：" + count + "，延时等级：" + delayTimeLevel);
//                        log.info("原始字符串：{},截取后：{}，次数：{}，延时等级：{}", body, substring, count, delayTimeLevel);

                            SendMsgDTO sendMsgDTO = JSON.parseObject(body,SendMsgDTO.class);
                            String payNo = sendMsgDTO.getPayNo();
                            int retryTime = sendMsgDTO.getRetryTime();
                            System.out.println("payNo：" + payNo + "，次数：" + retryTime + "，延时等级：" + delayTimeLevel);

                            if(retryTime < 5){ // 小于3次进行重试
                                SendMsgDTO SendMsgDTO = new SendMsgDTO(payNo,retryTime,DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel));
                                list.add(SendMsgDTO);
                            }else{  // 多余3次进行持久化
                                save(payNo,retryTime,delayTimeLevel);
                            }
                        }
                        // 返回消费状态，这里是进行ACK确认
                        System.out.println("--------3333---------");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

                    }catch(Exception e){
                        // 消费失败后，进行消费重试。可以进行如下配置，含义是稍后消费
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

    private static void retrySend(List<SendMsgDTO> SendMsgDTOList){
        for (SendMsgDTO item : SendMsgDTOList) {
            retrySend(item.getPayNo(),item.getRetryTime(),item.getDelayTimeLevel().getLevel());
        }
    }

    /**
     * 发送到一定次数，进行持久化
     */
    private static void save(String payNo,int retryTime,int delayTimeLevel){
        System.out.println("进行持久化，payNo：" + payNo + "，重试次数：" + retryTime + "，延时等级：" + delayTimeLevel);
    }

    /**
     * 重新发送
     */
    private static void retrySend(String payNo,int count,int delayTimeLevel){
        try {
            DefaultMQProducer producer = new DefaultMQProducer("pg");
            producer.setNamesrvAddr("localhost:9876");
            // 指定异步发送失败后进行重试的次数
            producer.setRetryTimesWhenSendAsyncFailed(3);
            // 指定新创建的topic的Queue数量为3，默认为4，也就是queueId的值
            producer.setDefaultTopicQueueNums(3);

            producer.start();

//            String str = subStr + "_" + (count + 1);
//            System.out.println("重新发送：" + str);
//
//            byte[] body = str.getBytes();

            SendMsgDTO sendMsgDTO = new SendMsgDTO(payNo, count + 1);

            Message message = new Message("topic_test_ack","asycSend", JSON.toJSONString(sendMsgDTO).getBytes(StandardCharsets.UTF_8));
            message.setDelayTimeLevel(DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel + 1).getLevel());

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

