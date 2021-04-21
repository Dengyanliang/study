package com.deng.study;

import com.deng.study.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 16:53
 */
@Slf4j
@SpringBootTest
public class RedisSpringBootTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        // redisTemplate
        // opsForValue 操作字符串 类似于String
        // opsForList  操作List  类似于list
        // opsForHash
        // opsForGeo
        // opsForZSet
        // opsForSet
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.flushAll();

        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.opsForValue().set("key","value1");
        redisTemplate.exec();

        String value = (String) redisTemplate.opsForValue().get("key");
        log.info("value:{}",value);
    }

    @Test
    public void test2() throws JsonProcessingException {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.flushAll();

        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);

//        String jsonUser = new ObjectMapper().writeValueAsString(user);
//        redisTemplate.opsForValue().set("user",jsonUser);

        redisTemplate.opsForValue().set("user",user);

        Object value = redisTemplate.opsForValue().get("user");
        log.info("value:{}",value);
    }



}
