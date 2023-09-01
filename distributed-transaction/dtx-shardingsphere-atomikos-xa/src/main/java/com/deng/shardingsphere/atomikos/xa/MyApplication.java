package com.deng.shardingsphere.atomikos.xa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 12:02
 */
@MapperScan("com.deng.shardingsphere.atomikos.xa.mapper")
@SpringBootApplication
@EnableConfigurationProperties(DataSourceProperties.class)
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

