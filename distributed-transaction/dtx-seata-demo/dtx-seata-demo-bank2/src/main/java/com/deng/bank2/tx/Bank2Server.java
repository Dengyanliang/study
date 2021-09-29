package com.deng.bank2.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class Bank2Server {
    public static void main(String[] args) {
        SpringApplication.run(Bank2Server.class,args);
    }
}
