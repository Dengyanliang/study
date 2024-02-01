package com.deng.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/22 11:33
 */
@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.deng.employee"})
// 扫描feign定义的包路径
@EnableFeignClients(basePackages = {"com.deng.employee"})
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class,args);
    }
}
