package com.deng.bank1.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = "com.deng.bank1.tx")
public class Bank1Server {
    public static void main(String[] args) {
        SpringApplication.run(Bank1Server.class,args);
    }
}
