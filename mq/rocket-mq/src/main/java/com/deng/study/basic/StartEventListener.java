package com.deng.study.basic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

/**
 * @Desc: 启动时获取配置信息
 * @Date: 2024/2/18 09:24
 * @Auther: dengyanliang
 */
@Configuration
public class StartEventListener {
    private static final Logger log = LoggerFactory.getLogger(StartEventListener.class);

    public StartEventListener(){

    }

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class})
    public void afterStart(WebServerInitializedEvent event){
        Environment environment = event.getApplicationContext().getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        int localPort = event.getWebServer().getPort();
        String profile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
        log.info("---[{}]---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", appName, localPort, profile);
    }

}
