package com.deng.study.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/5/9 07:38
 */
public class MyJDKProxy implements InvocationHandler {

    private Object target;

    public MyJDKProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return ((RealHello)target).testInvoke();
    }
}
