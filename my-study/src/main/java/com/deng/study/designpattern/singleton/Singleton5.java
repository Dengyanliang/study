package com.deng.study.designpattern.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法五、懒汉式 双重检查
 * 此写法，支持延迟加载，线程安全，加锁粒度减小，并发性有一定提升，添加volatile解决了重排序
 * 同时也存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton5 implements Singleton, Serializable,Cloneable {

    private static volatile Singleton5 instance;

    public static Singleton5 getInstance(){
        if(instance == null){
            synchronized (Singleton5.class){
                if(instance == null){
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }

    private Singleton5() {
        System.out.println("Singleton5....");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
