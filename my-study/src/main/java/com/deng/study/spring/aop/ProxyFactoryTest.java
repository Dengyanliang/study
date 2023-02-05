package com.deng.study.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJAfterAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/1 20:44
 */
public class ProxyFactoryTest {
    public static void main(String[] args) {
        // aspect = 通知（advice）+ 切点（pointcut），一个切面中可能有一到多个通知方法
        // advisor = 更细粒度的切面，包含一个通知和切点

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Target1());      // 设置目标对象

        // 第一种情况，所有方法都加切面
//        proxyFactory.addAdvice((MethodInterceptor) methodInvocation -> { // 添加通知，MethodInterceptor是环绕通知
//            String methodName = methodInvocation.getMethod().getName();
//            try {
//                System.out.println(methodName +" before...");
//                return methodInvocation.proceed();
//            }finally {
//                System.out.println(methodName + " after...");
//            }
//        });

        // 第二种情况，针对特定方法添加通知
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())"); // 只是针对foo方法添加了通知

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(pointcut, (MethodInterceptor) methodInvocation -> {
            String methodName = methodInvocation.getMethod().getName();
            try {
                System.out.println(methodName +" before...");
                return methodInvocation.proceed();
            }finally {
                System.out.println(methodName + " after...");
            }
        }));

        // 这里按照jdk动态代理
//        proxyFactory.addInterface(I1.class);
//        I1 proxy = (I1)proxyFactory.getProxy(); // 创建代理对象

//        proxyFactory.setProxyTargetClass(true); // true使用cglib代理

        // 这里按照cglib动态代理。
        Target1 proxy = (Target1)proxyFactory.getProxy(); // 创建代理对象
        System.out.println(proxy.getClass());
        proxy.foo();
        System.out.println("--------------------");
        proxy.bar();
    }

    interface I1{
        void foo();
        void bar();
    }

    static class Target1 implements I1{
        @Override
        public void foo() {
            System.out.println("target1 foo");
        }

        @Override
        public void bar() {
            System.out.println("target1 bar");
        }
    }

}
