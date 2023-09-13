package com.deng.hmily.tcc.bank1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;




/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:54
 */
@SpringBootApplication(scanBasePackages= {"com.deng.hmily.tcc.bank1","org.dromara.hmily"})
@EnableDiscoveryClient
@EnableHystrix
@MapperScan("com.deng.hmily.tcc.bank1.dao.mapper")
@EnableFeignClients(basePackages = "com.deng.hmily.tcc.bank1.remote.client")
public class Bank1Application {
    public static void main(String[] args) {
        SpringApplication.run(Bank1Application.class,args);
    }
}
