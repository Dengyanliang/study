package com.deng.study.rocketmq.consumer;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Desc:
 * @Date: 2024/4/5 17:44
 * @Auther: dengyanliang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RocketMqConsumerConfiguration.class})
public @interface EnableRocketMqConsumer {
}
