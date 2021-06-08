package com.deng.study.java.proxy;

import org.junit.platform.commons.util.ClassLoaderUtils;
import sun.misc.ClassLoaderUtil;

import java.lang.reflect.Proxy;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/5/9 07:40
 */
public class TestProxy {

    public static void main(String[] args) {
        MyJDKProxy proxy = new MyJDKProxy(new RealHello());
        ClassLoader classLoader = ClassLoaderUtils.getDefaultClassLoader();

        Hello test = (Hello) Proxy.newProxyInstance(classLoader,new Class[]{Hello.class},proxy);
        System.out.println(test.say());

    }
}
