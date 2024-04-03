package com.deng.study.rocketmq.producer;

import com.deng.study.rocketmq.common.RocketMqProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.DisposableBean;

/**
 * @Desc:
 * @Date: 2024/4/3 20:20
 * @Auther: dengyanliang
 */
@Slf4j
@Data
public class SimpleProducer implements DisposableBean {
    private String producerGroup = "DEFAULT_PRODUCT_GROUP";
    private RocketMqProperties rocketMqProperties;
    private DefaultMQProducer producer;

    public SimpleProducer(RocketMqProperties rocketMqProperties){
        this.rocketMqProperties = rocketMqProperties;
    }

    public void init() throws MQClientException {
        RPCHook aclClientRPCHook = new AclClientRPCHook(new SessionCredentials(rocketMqProperties.getAccessKey(), rocketMqProperties.getSecretKey()));
        producer = new DefaultMQProducer(producerGroup,aclClientRPCHook);
        producer.setNamesrvAddr(rocketMqProperties.getNameServer());
        producer.setVipChannelEnabled(false);
        producer.start();
    }


    @Override
    public void destroy() throws Exception {
        log.info(">>>>>>> rocketmq producer shutdown");
        producer.shutdown();
    }
}
