package com.deng.tcc.account.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:54
 */
@SpringBootApplication(scanBasePackages= {"com.deng.tcc.account.tx","org.dromara.hmily"})
@EnableDiscoveryClient
@EnableHystrix
@MapperScan("com.deng.tcc.account.tx.dao.mapper")
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
    }
}
