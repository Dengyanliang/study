package com.deng.study.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@MapperScan("com.deng.study.shardingsphere.dao.mapper")
@EnableTransactionManagement
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@SpringBootApplication
//@EnableConfigurationProperties(DataSourceProperties.class)
public class ShardingShpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingShpereApplication.class, args);
    }

}

