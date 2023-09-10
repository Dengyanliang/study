package com.deng.hmily.tcc.storage.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@SpringBootApplication(scanBasePackages= {"com.deng.hmily.tcc.storage.tx","org.dromara.hmily"})
@EnableDiscoveryClient
@MapperScan("com.deng.hmily.tcc.storage.tx.dao.mapper")
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class,args);
    }
}
