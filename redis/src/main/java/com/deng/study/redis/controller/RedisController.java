package com.deng.study.redis.controller;

import com.deng.study.redis.service.RedisLockService;
import com.deng.study.redis.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisLockService redisLockService;

    @RedisLock(key = "redis_lock")
    @GetMapping("/index")
    public String index(){
        return "index";
    }


    @GetMapping("/sale")
    public String getSale(){
        String key = "inventoryOO1";
        return redisLockService.sale(key);
    }
}
