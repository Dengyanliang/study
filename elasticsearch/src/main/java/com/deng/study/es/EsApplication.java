package com.deng.study.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Date: 2024/1/5 14:15
 * @Auther: dengyanliang
 */
@MapperScan("com.deng.study.es.dao")
@SpringBootApplication
public class EsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class,args);
    }
}