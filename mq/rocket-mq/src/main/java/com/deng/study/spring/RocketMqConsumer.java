package com.deng.study.spring;

import com.alibaba.fastjson.JSON;
import com.deng.study.dto.SendMsgDTO;
import com.deng.study.enums.DelayTimeLevelEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.deng.study.common.MqConstant.*;

/**
 * @Desc:
 * @Date: 2024/2/4 12:46
 * @Auther: dengyanliang
 */

@Component
public class RocketMqConsumer implements MessageListenerConcurrently {

    private final Logger logger = LoggerFactory.getLogger(RocketMqConsumer.class);

    private DefaultMQPushConsumer consumer;

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    @Autowired
    private RocketMqProducer rocketMqProducer;


    /**
     * mq 初始化
     */
    @PostConstruct
    private void consumerInit(){
        try {
            logger.info("RocketMqConsumer 开始启动.....");
            consumer = new DefaultMQPushConsumer(groupName);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setVipChannelEnabled(false); // TODO
            consumer.subscribe(UNIONPAY_CALLBACK_TOPIC,UNIONPAY_CALLBACK_TAG); // 只消费topic下的tag信息
            consumer.registerMessageListener(this); // 设置监听

            consumer.start();
            logger.info("RocketMqConsumer 启动成功.....");
        } catch (MQClientException e) {
            logger.error("RocketMqConsumer 启动出现异常....",e);
        }
    }

    /**
     * 监听消息
     * @param msgs
     * @param consumeConcurrentlyContext
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        List<SendMsgDTO> sendMsgList = new ArrayList<>();
        try{
            // 逐条消费消息
            for (MessageExt msg : msgs) {
                String body = new String(msg.getBody());
                SendMsgDTO sendMsgDTO = JSON.parseObject(body, SendMsgDTO.class);

                String payNo = sendMsgDTO.getPayNo();
                int retryTime = sendMsgDTO.getRetryTime();
                int delayTimeLevel = msg.getDelayTimeLevel();

                logger.info("接收到的消息 payNo：{}，重试次数：{}，延时等级：{}", payNo, retryTime, delayTimeLevel);

                if(retryTime < 5){ // 小于5次进行重试
                    SendMsgDTO resendMsg = new SendMsgDTO(payNo,retryTime,DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel));
                    sendMsgList.add(resendMsg);
                }else{  // 多余5次进行持久化
                    saveDB(sendMsgDTO);
                }
            }
            // 返回消费状态，这里是进行ACK确认
            logger.info("--------进行ack---------");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch(Exception e){
            logger.error("消费消息时出现异常",e);
            // 消费失败后，进行消费重试
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }finally{
            if(CollectionUtils.isNotEmpty(sendMsgList)){
                logger.info("--------retrySend 开始---------");
                retrySend(sendMsgList);
                logger.info("--------retrySend 结束---------");
            }
        }
    }

    /**
     * 发送到一定次数，进行持久化
     */
    private void saveDB(SendMsgDTO sendMsgDTO){
        logger.info("进行持久化，sendMsgDTO：{}", sendMsgDTO);
    }

    private void retrySend(List<SendMsgDTO> sendMsgList){
        for (SendMsgDTO item : sendMsgList) {
            retrySend(item.getPayNo(),item.getRetryTime(),item.getDelayTimeLevel().getLevel());
        }
    }

    /**
     * 重新发送
     */
    private void retrySend(String payNo,int count,int delayTimeLevel){
        try {
            // 次数加1
            SendMsgDTO sendMsgDTO = new SendMsgDTO(payNo, count + 1);
            // 延迟级别加1
            rocketMqProducer.sendAysnc(UNIONPAY_CALLBACK_TOPIC, UNIONPAY_CALLBACK_TAG, sendMsgDTO, DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel + 1));

        } catch (Exception e) {
            logger.error("retrySend时出现异常",e);
            throw new RuntimeException(e);
        }
    }

}
