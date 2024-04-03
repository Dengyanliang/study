package com.deng.study.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import com.deng.study.rocketmq.common.RocketMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Desc:
 * @Date: 2024/4/3 20:27
 * @Auther: dengyanliang
 */
@Slf4j
@EnableConfigurationProperties(value = RocketMqProperties.class)
public class RocketMqProducerConfiguration {

    @Bean
    public SimpleProducer simpleProducer(RocketMqProperties rocketMqProperties) {
        log.info("初始化RocketMq生产者-开始");
        if(!rocketMqProperties.checkConfig()){
            log.warn("rocketmq》》》》》配置不正确:{}", JSON.toJSONString(rocketMqProperties));
            throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
        }

        try{
            SimpleProducer producer = new SimpleProducer(rocketMqProperties);
            producer.init();
            log.info("初始化RocketMq生产者-结束");

            return producer;
        }catch(Exception e){
            log.error("初始化RocketMq生产者失败,{}", e.getMessage(), e);
            throw new BeanCreationException("rocketmq》》》》》配置不正确，请检查配置");
        }
    }

    @Bean
    public ProducerService producerService(SimpleProducer simpleProducer){
        ProducerService producerService = new ProducerService();
        producerService.setSimpleProducer(simpleProducer);
        return producerService;
    }

}
