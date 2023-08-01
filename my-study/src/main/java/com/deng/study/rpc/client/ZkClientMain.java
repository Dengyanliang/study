package com.deng.study.rpc.client;


import com.deng.study.common.constant.ZkConstant;
import com.deng.study.rpc.service.HelloService;
import com.deng.study.rpc.service.client.DiscoveryService;
import com.deng.study.rpc.service.client.DiscoveryServiceImpl;

public class ZkClientMain {

    public static void main(String[] args) {

        // 从注册中心发现，所以连接的是注册中心的地址
        DiscoveryService discoveryService = new DiscoveryServiceImpl(ZkConstant.CONNECT_STRING);
        for (int i = 1; i < 5; i++){
            String version = "";
            if(i < 2){
                version = i+".0";
            }

            ZkRpcClientProxy clientProxy = new ZkRpcClientProxy(discoveryService,version);

            HelloService sericeProxy = clientProxy.createProxy(HelloService.class);
            String result = sericeProxy.sayHello("你好！");
            System.out.println("sericeProxy.sayHello response:"+result);
        }
    }


}
