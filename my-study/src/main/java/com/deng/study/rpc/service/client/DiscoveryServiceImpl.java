package com.deng.study.rpc.service.client;


import com.deng.study.rpc.common.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class DiscoveryServiceImpl implements DiscoveryService {

    private CuratorFramework curatorFramework;

    private List<String> serviceNodes;

    public DiscoveryServiceImpl(String address){
        curatorFramework = CuratorFrameworkFactory.builder().connectString(address).
                sessionTimeoutMs(ZkConfig.SESSTION_TIMEOUT).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();
    }


    @Override
    public String discovery(String serviceName) {
        try {
            // 根据路径获取所有节点
            String serviceUrl = ZkConfig.PATH + ZkConfig.OBLIQUE_LINE + serviceName;
            System.out.println("serviceUrl:"+ serviceUrl);

            serviceNodes = curatorFramework.getChildren().forPath(serviceUrl);

            // 发起服务监听
            registerWatcher(serviceUrl);

            // 实现负载均衡
            LoadBalanceService loadBalanceService = new RandomBalanceServiceImpl();
            return loadBalanceService.selectHost(serviceNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void registerWatcher(String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                serviceNodes = curatorFramework.getChildren().forPath(path);
            }
        };

        pathChildrenCache.getListenable().addListener(listener);
        pathChildrenCache.start();
    }


}
