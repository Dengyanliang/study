package com.deng.study.rpc.server;

import java.io.Serializable;

/**
 * 必须实现序列化
 */
public class HelloServiceImpl implements Serializable,HelloService{
    @Override
    public String sayHello(String str) {
        String result = "hello," + str;
        System.out.println(result);
        return result;
    }
}
