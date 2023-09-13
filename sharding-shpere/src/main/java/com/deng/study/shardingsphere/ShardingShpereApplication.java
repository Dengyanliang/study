package com.deng.study.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@MapperScan("com.deng.study.shardingsphere.dao.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShardingShpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingShpereApplication.class, args);
    }

}

