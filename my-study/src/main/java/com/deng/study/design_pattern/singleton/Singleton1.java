package com.deng.study.design_pattern.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法一、饿汉式
 * 此写法，线程安全，但是不支持延迟加载，同时也存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 09:21
 */
public class Singleton1 implements Singleton , Serializable,Cloneable {

    private static Singleton1 instance = new Singleton1();

    private Singleton1(){
        System.out.println("Singleton1....");
    }

    public static Singleton1 getInstance(){
        return instance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
