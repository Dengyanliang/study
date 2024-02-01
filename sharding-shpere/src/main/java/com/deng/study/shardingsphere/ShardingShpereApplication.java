package com.deng.study.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@MapperScan("com.deng.study.shardingsphere.dao.mapper")
// keypoint 如果sharding-jdbc没配置默认的连接数据库，需要在启动类中屏蔽shardingsphere...SpringBootConfiguration自动装载的类
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SpringBootConfiguration.class})
public class ShardingShpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingShpereApplication.class, args);
    }

}

