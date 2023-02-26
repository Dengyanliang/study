package com.deng.study.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RedisLock(key = "redis_lock")
    @GetMapping("/index")
    public String index(){
        return "index";
    }


    @GetMapping("/sale")
    public String getSale(){
        String key = "inventoryOO1";
        return redisService.sale(key);
    }
}
