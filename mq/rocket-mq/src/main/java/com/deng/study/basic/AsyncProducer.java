package com.deng.study.basic;

import com.alibaba.fastjson.JSON;
import com.deng.study.dto.SendMsgDTO;
import com.deng.study.enums.DelayTimeLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Date: 2024/2/2 13:53
 * @Auther: dengyanliang
 */
@Slf4j
public class AsyncProducer {
    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("pg");
            producer.setNamesrvAddr("localhost:9876");
            // 指定异步发送失败后进行重试的次数
            producer.setRetryTimesWhenSendAsyncFailed(3);
            // 指定新创建的topic的Queue数量为3，默认为4，也就是queueId的值
            producer.setDefaultTopicQueueNums(3);

            producer.start();

            for (int i = 0; i < 1; i++) {
                int level = DelayTimeLevelEnum.DELAY_0s.getLevel();
//                byte[] body = ("pay_order_" + i + "_" + level).getBytes();

                SendMsgDTO sendMsgDTO = new SendMsgDTO("pay_order_" + i, 0);

                Message message = new Message("topic_test_ack","asycSend", JSON.toJSONString(sendMsgDTO).getBytes(StandardCharsets.UTF_8));
                message.setDelayTimeLevel(level);

                // 异步发送，指定回调
                producer.send(message, new SendCallback() {
                    // 当producer接收到MQ发送过来的ACK后，就会触发该回调方法的执行
                    @Override
                    public void onSuccess(SendResult sendResult) {
//                        log.info(sendResult.toString());
                        System.out.println(sendResult);
                    }

                    // 当
                    @Override
                    public void onException(Throwable e) {
                        log.info(e+"");
                    }
                });
            }
            // 睡眠3秒。这个休眠很重要，否则消费没有发送，执行shutdown会报connect to null failed
            TimeUnit.SECONDS.sleep(3);
            producer.shutdown();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
