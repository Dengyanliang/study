package com.deng.study.designpattern.creational.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法三、懒汉式 getInstance方法加锁
 * 此写法，支持延迟加载，线程安全，但是在getInstance方法加锁粒度太大，同时也存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton3 implements Singleton, Serializable,Cloneable {

    private static Singleton3 instance;

    public static synchronized Singleton3 getInstance(){
        if(instance == null){
            instance = new Singleton3();
        }
        return instance;
    }

    private Singleton3() {
        System.out.println("Singleton3....");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
