package com.deng.jta.atomikos.xa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 12:02
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

