package com.deng.study.redis.aspect;

import com.deng.study.redis.lock.RedisDistributedLock;
import com.deng.study.redis.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LockMethodAspect {

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Around("@annotation(com.deng.study.redis.lock.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String key = redisLock.key();
        String value = UUID.randomUUID().toString();

        try {
            boolean isLock = redisDistributedLock.tryLock(key, value, redisLock.waitTime(),redisLock.expire(),redisLock.timeUnit());
            log.info("key:{},value:{},isLock:{}",key,value,isLock);
            if (!isLock) {
                log.error("获取锁失败");
                throw new RuntimeException("获取锁失败");
            }
            return joinPoint.proceed();
        } catch (Throwable e){
            throw new RuntimeException("系统异常");
        } finally {
            log.info("释放锁");
            redisDistributedLock.unlock();
        }
    }

}
