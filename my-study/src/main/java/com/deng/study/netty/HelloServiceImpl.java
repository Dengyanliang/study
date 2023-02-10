package com.deng.study.netty;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:42
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String message) {
        return "你好，" + message;
    }
}
