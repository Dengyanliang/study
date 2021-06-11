package com.deng.study.rpc.service.client;

import com.deng.study.rpc.common.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.Objects;

/**
 * 注册接口
 */
public class RegisterServiceImpl implements IRegisterService{

    private CuratorFramework curatorFramework;

    public RegisterServiceImpl() {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(ZkConfig.CONNECT_STRING).
                sessionTimeoutMs(ZkConfig.SESSTION_TIMEOUT).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();
    }

    /**
     * 注册服务名称为根节点下的持久化节点
     * 注册服务对应的服务器地址为服务名称节点下的临时节点
     * 每次服务重启时会清空临时节点（服务的分布式服务器），并重新注册临时节点
     *
     * @param serviceName
     * @param serviceAddress
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        // 注册相应的服务
        String serviceUrl = ZkConfig.PATH + ZkConfig.OBLIQUE_LINE + serviceName;
        try {
            if(Objects.isNull(curatorFramework.checkExists().forPath(serviceUrl))){
                // 持久节点
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serviceUrl,serviceName.getBytes());
            }

            String addressUrl = serviceUrl + ZkConfig.OBLIQUE_LINE + serviceAddress;
            // 临时节点
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressUrl,addressUrl.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
