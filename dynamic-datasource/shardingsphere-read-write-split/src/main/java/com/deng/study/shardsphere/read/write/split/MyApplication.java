package com.deng.study.shardsphere.read.write.split;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/10/18 18:13
 */
@MapperScan("com.deng.study.shardsphere.read.write.split.mapper")
@EnableConfigurationProperties(DataSourceProperties.class)
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }
}