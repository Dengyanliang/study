package com.deng.seata.order.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@MapperScan(basePackages = "com.deng.seata.order.tx.dao.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 将springboot应用程序注册到注册中心
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = "com.deng.seata.order.tx.remote")
public class OrderServer {
    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class,args);
    }
}
