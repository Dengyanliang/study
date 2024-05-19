package com.deng.seata.tcc.storage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

/**
 * @Desc:
 * @Date: 2024/5/17 15:48
 * @Auther: dengyanliang
 */
@EnableDubbo
@ImportResource(value = {"classpath*:springxml/*.xml"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StorageServer {
    public static void main(String[] args) {
        SpringApplication.run(StorageServer.class,args);
    }
}