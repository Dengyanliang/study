package com.deng.study;

import com.deng.study.common.configuration.MyDataSourceConfig;
import com.deng.study.domain.Hello;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Slf4j
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@ImportResource("classpath:applicationContext.xml")
@MapperScan("com.deng.study.dao.mapper")
@EnableJms //启动消息队列
@Import({MyDataSourceConfig.class})
@EnableSwagger2
public class MyApplication {

    public static void main(String[] args) {

//        ApplicationContext context = new AnnotationConfigApplicationContext(MyApplication.class);
//        Hello hello = context.getBean(Hello.class);
//        System.out.println(hello);

        SpringApplication.run(MyApplication.class, args);
    }

}