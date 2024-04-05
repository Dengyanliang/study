package com.deng.study.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.deng.study.rocketmq.common.RocketMqProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Desc:
 * @Date: 2024/4/5 16:05
 * @Auther: dengyanliang
 */
@Slf4j
public class SimpleConsumerConfigurate implements InitializingBean, DisposableBean {

    private RocketMqProperties rocketMqProperties;
    private GenericApplicationContext genericApplicationContext;

    private List<SimpleConsumer> consumerList;


    public SimpleConsumerConfigurate(RocketMqProperties rocketMqProperties, GenericApplicationContext genericApplicationContext) {
        this.rocketMqProperties = rocketMqProperties;
        this.genericApplicationContext = genericApplicationContext;
    }

    /**
     * chagee-c-rocketmq:
     *   name-server: rmq-cn-5yd3j517o2f.cn-shanghai.rmq.aliyuncs.com:8080
     *   access-key: 1Dxs8PLSik35PDwW
     *   secret-key: 0j49o5z454g75Z5t
     *   group-param-list:
     *     - group-name: chagee-c-payment-group
     *       consumer-param-list:
     *         - consumer-listener: com.chagee.payment.server.mq.TradeResendMqConsumer
     *           topic: TRADE_RESEND_MSG_TOPIC
     *           selectorExpression: TRADE_RESEND_MSG_TAG
     *         - consumer-listener: com.chagee.payment.server.mq.UnionPayMqConsumer
     *           topic: UNIONPAY_CALLBACK_TOPIC
     *           selectorExpression: UNIONPAY_CALLBACK_TAG
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("自动注入RocketMq消费者Bean-开始");
        rocketMqProperties.getGroupParamList().forEach(g->{
            if(!g.checkConfig()){
                log.warn("rocketmq》》》》》配置不正确,{}", JSON.toJSONString(g));
                throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
            }

            g.getConsumerParamList().forEach(c->{
                if(!c.checkConfig()){
                    throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
                }
                try {
                    Class<?> consumerListenerClass = Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).loadClass(c.getConsumerListener());
                    String beanName = g.getGroupName()+"_"+c.getTopic()+"_"+consumerListenerClass.getSimpleName();
                    genericApplicationContext.registerBean(beanName,consumerListenerClass);

                    ConsumerConfig consumerConfig = new ConsumerConfig();
                    consumerConfig.setConsumerGroup(g.getGroupName());
                    consumerConfig.setTopic(c.getTopic());
                    consumerConfig.setSelectorExpression(c.getSelectorExpression());

                    MessageListener messageListener = (MessageListener) genericApplicationContext.getBean(beanName);
                    SimpleConsumer simpleConsumer = new SimpleConsumer(rocketMqProperties, consumerConfig);
                    simpleConsumer.setMessageListener(messageListener);
                    simpleConsumer.init();

                    log.info("初始化RocketMq消费者{}完成",consumerListenerClass.getSimpleName());
                    consumerList.add(simpleConsumer);
                } catch (Exception e) {
                    log.error("创建RocketMq消费者Bean失败,{}", e.getMessage(), e);
                    throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
                }
            });
        });

        log.info("自动注入RocketMq消费者Bean-完成");
    }

    @Override
    public void destroy() throws Exception {
        log.info(">>>>>>> rocketmq consumer shutdown");
        if(CollectionUtils.isNotEmpty(consumerList)){
            consumerList.forEach(SimpleConsumer::destory);
        }
    }

}
