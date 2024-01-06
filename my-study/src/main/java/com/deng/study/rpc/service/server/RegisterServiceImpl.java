package com.deng.study.rpc.service.server;

import com.deng.common.constant.ZkConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.Objects;

/**
 * 注册接口
 */
public class RegisterServiceImpl implements RegisterService {

    private CuratorFramework curatorFramework;

    public RegisterServiceImpl() {
        // 重试策略
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        curatorFramework = CuratorFrameworkFactory.builder().connectString(ZkConstant.CONNECT_STRING).
                sessionTimeoutMs(ZkConstant.SESSTION_TIMEOUT).
                retryPolicy(retryPolicy).build();
        curatorFramework.start();
    }

    /**
     * 注册服务名称为根节点下的持久节点
     * 注册服务对应的服务器地址为服务名称节点下的临时节点
     * 每次服务重启时会清空临时节点（服务的分布式服务器），并重新注册临时节点
     *
     * @param serviceName
     * @param serviceAddress
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        // 注册相应的服务
        String serviceUrl = ZkConstant.PATH + ZkConstant.OBLIQUE_LINE + serviceName;
        System.out.println("serviceUrl:"+ serviceUrl);
        try {
            if(Objects.isNull(curatorFramework.checkExists().forPath(serviceUrl))){
                // 注册服务名称为根节点下的持久节点
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(serviceUrl,serviceName.getBytes());
            }

            String addressUrl = serviceUrl + ZkConstant.OBLIQUE_LINE + serviceAddress;
            System.out.println("addressUrl:"+ addressUrl);
            // 注册服务对应的服务器地址为服务名称节点下的临时节点
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressUrl,addressUrl.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
