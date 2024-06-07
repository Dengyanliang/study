package com.deng.dtx.leaf.id;


import com.sankuai.inf.leaf.plugin.annotation.EnableLeafServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Desc:
 * @Date: 2024/5/21 13:41
 * @Auther: dengyanliang
 */
@EnableLeafServer
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class IdApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdApplication.class,args);
    }
}