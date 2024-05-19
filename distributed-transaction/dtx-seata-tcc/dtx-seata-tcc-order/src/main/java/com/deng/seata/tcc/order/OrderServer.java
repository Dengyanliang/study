package com.deng.seata.tcc.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@EnableDubbo
@ImportResource(value = {"classpath*:springxml/*.xml"})
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.deng.seata.tcc.order.remote")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OrderServer {
    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class,args);
    }
}
