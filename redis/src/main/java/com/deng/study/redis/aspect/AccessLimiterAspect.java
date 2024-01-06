package com.deng.study.redis.aspect;

import com.deng.study.redis.annotation.AccessLimiter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Date: 2024/1/6 17:46
 * @Auther: dengyanliang
 */
@Slf4j
@Aspect
@Component
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    @Around("@annotation(com.deng.study.redis.annotation.AccessLimiter)")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        AccessLimiter accessLimiter = method.getAnnotation(AccessLimiter.class);
        String methodKey = accessLimiter.methodKey();
        int limit = accessLimiter.limit();

        // 如果没有设置methodKey，从调用方法签名自动生成一个key
        if(StringUtils.isBlank(methodKey)){
            Class<?>[] parameterTypes = method.getParameterTypes();
            methodKey = method.getName();
            // 如果形参不空，则用参数拼接生成一个key；如果为空，则直接取方法名称
            if(ArrayUtils.isNotEmpty(parameterTypes)){
                String paramTypes = Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(","));
                log.debug("拼接生成后的methodKey：{}",methodKey);
                methodKey += "#" + paramTypes;
            }
        }

        try {
            // 执行lua脚本
            boolean acquired = stringRedisTemplate.execute(
                    rateLimitLua, // Lua script的真身
                    Lists.newArrayList(methodKey), // Lua脚本中的Key列表
                    Integer.toString(limit)  // Lua脚本Value列表
            );

            if(!acquired){
                log.error("Your access is blocked, threadName={}",Thread.currentThread().getName());
                throw new RuntimeException("Your access is blocked");
            }

            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("执行限流时发生了异常，e:{}",e);
            throw new RuntimeException(e);
        }
    }


}
