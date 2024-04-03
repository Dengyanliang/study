package com.deng.study.rocketmq.producer;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Desc:
 * @Date: 2024/4/3 20:41
 * @Auther: dengyanliang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RocketMqProducerConfiguration.class)
public @interface EnableRocketMqProducer {

}
