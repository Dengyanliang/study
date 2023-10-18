package com.deng.study.dynamic.datasource;

import com.deng.study.dynamic.datasource.datasouce.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/10/17 16:39
 */
@Import(DynamicDataSourceRegister.class)
@MapperScan("com.deng.study.dynamicdatasource.mapper")
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }
}
