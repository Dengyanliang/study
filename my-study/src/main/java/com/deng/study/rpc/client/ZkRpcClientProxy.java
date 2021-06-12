package com.deng.study.rpc.client;

import com.deng.study.rpc.service.client.DiscoveryService;

import java.lang.reflect.Proxy;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/12 08:32
 */
public class ZkRpcClientProxy {
    private DiscoveryService discoveryService;

    private String version;

    public ZkRpcClientProxy(DiscoveryService discoveryService, String version) {
        this.discoveryService = discoveryService;
        this.version = version;
    }

    public <T> T createProxy(final Class<T> interfaces){
        return (T)Proxy.newProxyInstance(interfaces.getClassLoader(),new Class[]{interfaces},
                new RemoteInvocationHandler(discoveryService,version));
    }
}
