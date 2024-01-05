package com.deng.study.common.configuration;

import com.deng.common.constant.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/21 14:40
 */
@Configuration
public class ZkConfig {

    @Bean
    public CuratorFramework curatorFramework(){
        // 初始化一个重试策略，这里使用的指数补偿策略。初始间隔时间 重试次数
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 初始化curator客户端
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(ZkConstant.CONNECT_STRING).
                sessionTimeoutMs(ZkConstant.SESSTION_TIMEOUT).
                retryPolicy(retryPolicy).build();
        // keypoint 需要手动启动，否则很多方法或者功能是不工作的
        curatorFramework.start();
        return curatorFramework;
    }
}
