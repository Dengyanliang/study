package com.deng.study.redis.limiter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Date: 2024/1/5 17:55
 * @Auther: dengyanliang
 */
@Slf4j
@Service
public class AccessLimiterService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    public void limitAccess(String key,Integer limit){
        // keypoint 在执行lua的时候会报 'attempt to compare nil with number script'
        //  原因：1、在RedisConfig中声明的RedisTemplete: key--RedisSerializer.string(),value--jsonRedisSerializer
        //       2、执行lua脚本tonumber()函数，无法将json这个value转化为数字，所以result会变成nli，尝试将nli与数字脚本进行比较 会报错
        //  解决办法有两种：1）存入redis的value也是用RedisSerializer.string()
        //               2) 直接使用StringRedisTemplate

        // 执行lua脚本
        boolean acquired = stringRedisTemplate.execute(
                rateLimitLua, // Lua script的真身
                Lists.newArrayList(key), // Lua脚本中的Key列表
                limit.toString() // Lua脚本Value列表
        );

        if(!acquired){
            log.error("Your access is blocked, threadName={}",Thread.currentThread().getName());
            throw new RuntimeException("Your access is blocked");
        }

    }
}
