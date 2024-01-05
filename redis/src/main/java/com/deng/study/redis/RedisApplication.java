package com.deng.study.redis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc: redis学习
 * @Date: 2024/1/5 11:55
 * @Auther: dengyanliang
 */
@MapperScan("com.deng.study.mapper")
@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }
}
