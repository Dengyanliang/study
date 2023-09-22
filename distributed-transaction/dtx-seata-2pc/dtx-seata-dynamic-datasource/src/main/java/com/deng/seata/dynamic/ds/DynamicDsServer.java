package com.deng.seata.dynamic.ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:53
 */
@MapperScan(basePackages = "com.deng.seata.dynamic.ds.dao.mapper")
@SpringBootApplication
public class DynamicDsServer {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDsServer.class,args);
    }
}
