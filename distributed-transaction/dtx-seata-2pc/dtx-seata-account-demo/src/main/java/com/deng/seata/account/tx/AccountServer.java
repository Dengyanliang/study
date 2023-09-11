package com.deng.seata.account.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/11 15:48
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.deng.seata.account.tx.dao.mapper")
public class AccountServer {
    public static void main(String[] args) {
        SpringApplication.run(AccountServer.class,args);
    }
}
