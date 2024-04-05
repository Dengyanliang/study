package com.deng.study.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.deng.study.rocketmq.common.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @Desc:
 * @Date: 2024/4/5 16:03
 * @Auther: dengyanliang
 */
@Slf4j
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConsumerConfiguration {

    @Bean
    public SimpleConsumerConfigurate createConsumerListener(RocketMqProperties rocketMqProperties, ApplicationContext applicationContext){
        log.info("自动注入RocketMq消费者监听-开始");
        if(!rocketMqProperties.checkConfig() || CollectionUtils.isEmpty(rocketMqProperties.getGroupParamList())){
            log.warn("rocketmq》》》》》配置不正确:{}", JSON.toJSONString(rocketMqProperties));
            throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
        }
        SimpleConsumerConfigurate simpleConsumerConfigurate = new SimpleConsumerConfigurate(rocketMqProperties,(GenericApplicationContext)applicationContext);
        log.info("自动注入RocketMq消费者监听-结束");
        return simpleConsumerConfigurate;
    }
}
