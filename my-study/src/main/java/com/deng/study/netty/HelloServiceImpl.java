package com.deng.study.netty;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:42
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String message) {
        int i = 1 /0;
        return "你好，" + message;
    }
}
