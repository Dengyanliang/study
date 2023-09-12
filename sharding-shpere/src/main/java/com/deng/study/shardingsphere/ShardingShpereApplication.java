package com.deng.study.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@MapperScan("com.deng.study.shardingsphere.dao.mapper")
// 开启spring注解版事务功能，并往容器中注册DataSource、JdbcTemplate、PlatformTransactionManager这三个bean对象
//@EnableTransactionManagement
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
//@EnableConfigurationProperties(DataSourceProperties.class)
public class ShardingShpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingShpereApplication.class, args);
    }

}

