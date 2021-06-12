package com.deng.study.rpc.server;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/11 22:14
 */

import com.deng.study.rpc.common.ZkConfig;
import com.deng.study.rpc.service.HelloService;
import com.deng.study.rpc.service.HelloServiceImpl;
import com.deng.study.rpc.service.HelloServiceImpl2;
import com.deng.study.rpc.service.HelloServiceImpl3;
import com.deng.study.rpc.service.server.RegisterService;
import com.deng.study.rpc.service.server.RegisterServiceImpl;

public class ClusterServer1  {

    public static void main(String[] args) {
        // 业务接口，被调用的接口
        HelloService helloService = new HelloServiceImpl();

        // 多版本
        HelloService helloService2 = new HelloServiceImpl2();
        HelloService helloService3 = new HelloServiceImpl3();


        // 注册中心接口，业务接口会往注册接口上注册
        RegisterService registerService = new RegisterServiceImpl();

        ZkServer zkServer = new ZkServer(registerService, ZkConfig.ADDRESS1);
        zkServer.bind(helloService,helloService2,helloService3);
        zkServer.publishServer();
        System.out.println("服务1发布成功");
    }

}
