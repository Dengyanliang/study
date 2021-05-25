package com.deng.study.java.socket;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 获取项目注册的端口号
 */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent>{

    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }

    public int getServerPort() {
        return serverPort;
    }
}
