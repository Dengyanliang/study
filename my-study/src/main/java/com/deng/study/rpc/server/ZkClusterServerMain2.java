package com.deng.study.rpc.server;


/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/11 22:14
 */
import com.deng.study.rpc.service.HelloService;
import com.deng.study.rpc.service.HelloServiceImpl;
import com.deng.study.rpc.service.HelloServiceImpl2;
import com.deng.study.rpc.service.HelloServiceImpl3;
import com.deng.study.rpc.service.server.RegisterService;
import com.deng.study.rpc.service.server.RegisterServiceImpl;

public class ZkClusterServerMain2 {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();

        HelloService helloService2 = new HelloServiceImpl2();
        HelloService helloService3 = new HelloServiceImpl3();

        RegisterService registerService = new RegisterServiceImpl();
        ZkServer zkServer = new ZkServer(registerService, ZkConstant.ADDRESS2);
        zkServer.bind(helloService,helloService2,helloService3);
        zkServer.publishServer();
        System.out.println("服务2发布成功");
    }

}
