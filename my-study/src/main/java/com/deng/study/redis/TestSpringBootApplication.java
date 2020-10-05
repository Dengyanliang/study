package com.deng.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 16:31
 */
@Slf4j
@Component
public class TestSpringBootApplication {
    @Autowired
    private RedisTemplate redisTemplate;

    private void test(){
        // redisTemplate
        // opsForValue 操作字符串 类似于String
        // opsForList  操作List  类似于list
        // opsForHash
        // opsForGeo
        // opsForZSet
        // opsForSet
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.flushAll();

        redisTemplate.opsForValue().set("key","value1");
        redisTemplate.exec();

        String value = (String) redisTemplate.opsForValue().get("key");
        log.info("value:{}",value);

    }


    public static void main(String[] args) {
        new TestSpringBootApplication().test();
    }
}
