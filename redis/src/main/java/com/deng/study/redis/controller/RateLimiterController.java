package com.deng.study.redis.controller;

import com.deng.study.redis.limiter.AccessLimiter;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: 单机版的限流
 * @Date: 2024/1/5 15:51
 * @Auther: dengyanliang
 */
@Slf4j
@RestController
public class RateLimiterController {

    @Autowired
    private AccessLimiter accessLimiter;

    // 单机版--表示1秒中会产生2个令牌，也就是差不多0.5秒会放过1个请求过去
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    /**
     * 单机版--非阻塞式限流
     * @param count
     * @return
     */
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count){
        if(rateLimiter.tryAcquire(count)){
            log.info("success,rate is：{}",rateLimiter.getRate());
            return "success";
        }else{
            log.info("fail ,rate is：{}",rateLimiter.getRate());
            return "fail";
        }
    }

    /**
     * 单机版--限定时间内非阻塞限流，在给定时间内获取令牌
     * @param count
     * @param timeout
     * @return
     */
    @GetMapping("/tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count,Integer timeout){
        if(rateLimiter.tryAcquire(count,timeout, TimeUnit.SECONDS)){
            log.info("success,rate is：{}",rateLimiter.getRate());
            return "success";
        }else{
            log.info("fail ,rate is：{}",rateLimiter.getRate());
            return "fail";
        }
    }

    /**
     * 单机版--同步阻塞限流
     * @param count
     * @return
     */
    @GetMapping("/accquire")
    public String accquire(Integer count){
        rateLimiter.acquire(count);
        log.info("success,rate is：{}",rateLimiter.getRate());
        return "success";
    }

    /**
     * 分布式--限流
     * @return
     */
    @GetMapping("test")
    public String test(){
        accessLimiter.limitAccess("rateLimiter",1);
        return "access";
    }

}
