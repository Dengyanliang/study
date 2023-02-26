package com.deng.study.redis;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;



@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisLock {
    /**
     * 业务键
     * @return
     */
    String key();

    /**
     * 锁的过期时间
     * @return
     */
    int expire() default 5;

    /**
     * 尝试加锁，最多等待时间
     * @return
     */
    long waitTime() default Long.MAX_VALUE;

    /**
     * 锁的超时时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
