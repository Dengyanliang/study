package com.deng.hmily.tcc.bank2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:54
 */
@SpringBootApplication(scanBasePackages= {"com.deng.hmily.tcc.bank2","org.dromara.hmily"})
@EnableDiscoveryClient
@MapperScan("com.deng.hmily.tcc.bank2.dao.mapper")
public class Bank2Application {
    public static void main(String[] args) {
        SpringApplication.run(Bank2Application.class,args);
    }
}
