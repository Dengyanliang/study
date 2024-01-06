package com.deng.study.redis.annotation;

import java.lang.annotation.*;

/**
 * @Desc: 自定义注解限流
 * @Date: 2024/1/6 17:42
 * @Auther: dengyanliang
 */
@Target(ElementType.METHOD) // 方法增强
@Retention(RetentionPolicy.RUNTIME) // 运行周期，这里设置的运行级别
@Documented
@Inherited
public @interface AccessLimiter {
    /**
     * 允许通过的最大流量
     */
    int limit();

    /**
     * key的名称
     */
    String methodKey() default "";
}
