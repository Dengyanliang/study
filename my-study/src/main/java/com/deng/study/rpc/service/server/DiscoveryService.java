package com.deng.study.rpc.service.server;


import com.deng.study.rpc.common.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class DiscoveryService implements IDiscoveryService{

    private CuratorFramework curatorFramework;

    public DiscoveryService(String address){
        curatorFramework = CuratorFrameworkFactory.builder().connectString(address).
                sessionTimeoutMs(ZkConfig.SESSTION_TIMEOUT).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
    }


    @Override
    public String discovery(String serviceName) {
        try {
            // 根据路径获取所有节点
            String serviceUrl = ZkConfig.PATH + ZkConfig.OBLIQUE_LINE + serviceName;
            List<String> serviceNodes = curatorFramework.getChildren().forPath(serviceUrl);

            // 发起服务监听

            // 实现负载均衡






        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
