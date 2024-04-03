package com.deng.restroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 18:58
 */
@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.deng.restroom"})
public class RestRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestRoomApplication.class,args);

        // dubbo优雅停机
        // 关闭DubboBootstrap自动注册的DubboShutdownHook，只保留DubboBootstrapApplicationListener监听ContextClosedEvent关闭dubbo服务的功能
//        DubboBootstrap.getInstance().unRegisterShutdownHook();
    }
}
