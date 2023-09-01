package com.deng.tcc.order.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@SpringBootApplication(scanBasePackages= {"com.deng.tcc.order.tx","org.dromara.hmily"})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = "com.deng.tcc.order.tx.remote")
@MapperScan("com.deng.tcc.order.tx.dao.mapper")
public class OrderServer {
    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class,args);
    }
}