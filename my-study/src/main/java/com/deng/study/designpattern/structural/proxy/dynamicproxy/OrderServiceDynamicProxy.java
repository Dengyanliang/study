package com.deng.study.designpattern.structural.proxy.dynamicproxy;

import com.deng.study.designpattern.structural.proxy.Order;
import com.deng.study.designpattern.structural.proxy.db.DataSourceContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Desc: 动态代理
 * @Date: 2023/12/29 20:09
 * @Auther: dengyanliang
 */
public class OrderServiceDynamicProxy implements InvocationHandler {

    // 被代理的对象，也就是接口的实现类
    private Object target;

    public OrderServiceDynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * @return 代理类
     */
    public Object bind(){
        Class clazz = target.getClass();
        // 第一个参数是classLoader，第二个参数是target实现的所有接口，第三个参数是InvocationHandler
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    /**
     *
     * @param proxy 代理对象，很少使用？为啥还要使用呢？？
     * @param method 要被增强的方法对象
     * @param args 具体的method的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object argObject = args[0];
        beforeMethod(argObject);

        Object proxyObject = method.invoke(target,args);

        afterMethod();

        return proxyObject;
    }


    private void beforeMethod(Object obj){
        int userId = 0;
        System.out.println("动态代理 before code");
        if(obj instanceof Order){
            Order order = (Order)obj;
            userId = order.getUserId();
        }
        int dbRouter = userId % 2;
        System.out.println("动态代理分配到到【db"+dbRouter+"】处理数据");
        // 设置dataSource
        DataSourceContextHolder.setDBType("db"+dbRouter);

        System.out.println("动态代理 before code");
    }

    private void afterMethod(){
        // 清除dataSource
        DataSourceContextHolder.clearDBType();

        System.out.println("动态代理 after code");
    }

}
