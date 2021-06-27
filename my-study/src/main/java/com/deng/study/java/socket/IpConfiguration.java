package com.deng.study.java.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 获取项目注册的端口号:
 * 1
 * 2
 */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent>{

    private int serverPort;

    /**
     * get port
     */
    @Value("${server.port}")
    private int port;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }

    public int getServerPort() {
        return serverPort;
    }
}
