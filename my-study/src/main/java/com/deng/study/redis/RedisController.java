package com.deng.study.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @RedisLock(key = "redis_lock")
    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
