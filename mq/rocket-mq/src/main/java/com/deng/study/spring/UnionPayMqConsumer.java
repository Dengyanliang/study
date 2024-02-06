package com.deng.study.spring;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chagee.payment.common.enums.DelayTimeLevelEnum;
import com.chagee.payment.common.enums.PayOrderAbnormalStatus;
import com.chagee.payment.server.dto.SendMsgDTO;
import com.chagee.payment.server.dto.mini.MiniPayResultQueryDTO;
import com.chagee.payment.server.dto.resp.mini.MiniPayResultQueryResp;
import com.chagee.payment.server.entity.PayOrderAbnormalDO;
import com.chagee.payment.server.entity.PayOrderDO;
import com.chagee.payment.server.exception.UnionPayBizException;
import com.chagee.payment.server.mapper.PayOrderMapper;
import com.chagee.payment.server.service.IMiniPayService;
import com.chagee.payment.server.service.PayOrderAbnormalService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.chagee.payment.common.constant.MqConstant.UNIONPAY_CALLBACK_TAG;
import static com.chagee.payment.common.constant.MqConstant.UNIONPAY_CALLBACK_TOPIC;

/**
 * @Desc:
 * @Date: 2024/2/4 12:46
 * @Auther: dengyanliang
 */

@Component
public class UnionPayMqConsumer implements MessageListenerConcurrently {

    private final Logger logger = LoggerFactory.getLogger(UnionPayMqConsumer.class);

    private DefaultMQPushConsumer consumer;

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Autowired
    private IMiniPayService miniPayService;

    @Resource
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayOrderAbnormalService payOrderAbnormalService;


    /**
     * mq 初始化
     */
    @PostConstruct  // TODO 暂时去掉
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

                String orderNo = sendMsgDTO.getOrderNo();
                int retryTime = sendMsgDTO.getRetryTime();
                int delayTimeLevel = msg.getDelayTimeLevel();

                logger.info("接收到的消息 orderNo：{}，重试次数：{}，延时等级：{}", orderNo, retryTime, delayTimeLevel);

                // 1、如果数据库是终态，则直接忽略
                // 2、如果数据库是非终态并且渠道是终态，
                // 3、如果数据库是非终态并且渠道也是非终态并且重试次数<5，则进行重试

                // 查询数据库
                PayOrderDO payOrderDO = queryPayOrderDO(orderNo);
                if(Objects.nonNull(payOrderDO) && !payOrderDO.isEnd()){ // 非终态才处理
                    // TODO 查询渠道接口
                    MiniPayResultQueryResp unionPayResult = queryUnionPayResult();
                    if(unionPayResult.getStatus() == "fail"){  // 如果返回是终态，则更新数据库
                        UpdateWrapper<PayOrderDO> updateWrapper = new UpdateWrapper<>();
//                        updateWrapper.set("status",) // TODO
                        updateWrapper.eq("order_no",orderNo);
                        updateWrapper.eq("status",payOrderDO.getStatus());
                        payOrderMapper.update(updateWrapper);
                    }else{ //
                        if(retryTime < 5){ // 小于5次进行重试 TODO,应该是可配置的。
                            SendMsgDTO resendMsg = new SendMsgDTO(orderNo,retryTime,DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel));
                            sendMsgList.add(resendMsg);
                        }else{  // 多余5次进行持久化
                            savePayOrderAbnormal(payOrderDO);
                        }
                    }
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
    private void savePayOrderAbnormal(PayOrderDO payOrderDO){
        logger.info("进行持久化，payNo：{},orderNo:{}", payOrderDO.getPayNo(), payOrderDO.getOrderNo());

        PayOrderAbnormalDO payOrderAbnormalDO = new PayOrderAbnormalDO();
        BeanUtils.copyProperties(payOrderDO,payOrderAbnormalDO);
        payOrderAbnormalDO.setErrorType(PayOrderAbnormalStatus.NOT_QUERY_RESULT.getCode()); // 错误类型

        payOrderAbnormalService.save(payOrderAbnormalDO);
    }

    private void retrySend(List<SendMsgDTO> sendMsgList){
        for (SendMsgDTO item : sendMsgList) {
            retrySend(item.getOrderNo(),item.getRetryTime(),item.getDelayTimeLevel().getLevel());
        }
    }

    /**
     * 重新发送
     */
    private void retrySend(String payNo,int count,int delayTimeLevel){
        try {
            // 次数加1
            SendMsgDTO sendMsgDTO = new SendMsgDTO(payNo, count + 1);
            // TODO 延迟级别加1
            rocketMqProducer.sendAysnc(UNIONPAY_CALLBACK_TOPIC, UNIONPAY_CALLBACK_TAG, sendMsgDTO,DelayTimeLevelEnum.getDelayTimeLevel(delayTimeLevel + 1));

        } catch (Exception e) {
            logger.error("retrySend时出现异常",e);
            throw new RuntimeException(e);
        }
    }


    private MiniPayResultQueryResp queryUnionPayResult() throws UnionPayBizException {
        MiniPayResultQueryDTO queryDTO = new MiniPayResultQueryDTO();
        // TODO
        MiniPayResultQueryResp resultQueryResp = miniPayService.miniPayResultQuery(queryDTO);

//        MiniPayResultQueryResp resultQueryResp = new MiniPayResultQueryResp();
        resultQueryResp.setStatus("SUCCESS");

        return resultQueryResp;
    }

    private PayOrderDO queryPayOrderDO(String orderNo){
        QueryWrapper<PayOrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);

        return payOrderMapper.selectOne(queryWrapper);
    }

    /**
     * 把消息放入到MQ中，TODO 放在调用银联的地方
     * @param orderNo
     */
    public void putMQ(String orderNo){ // 这里应该存放orderNo，不是payNo
        SendMsgDTO sendMsgDTO = new SendMsgDTO(orderNo,0);
        rocketMqProducer.sendAysnc(UNIONPAY_CALLBACK_TOPIC, UNIONPAY_CALLBACK_TAG, sendMsgDTO);
    }
}
