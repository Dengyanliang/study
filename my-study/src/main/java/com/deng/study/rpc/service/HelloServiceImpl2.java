package com.deng.study.rpc.service;

import com.deng.study.rpc.common.RpcAnnotation;

import java.io.Serializable;

/**
 * 必须实现序列化
 */
@RpcAnnotation(value = HelloService.class,version = "2.0")
public class HelloServiceImpl2 implements Serializable, HelloService {
    @Override
    public String sayHello(String str) {
        String result = "hello," + str;
        System.out.println(result);
        return result;
    }
}
