package com.deng.study.designpattern.creational.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法四、懒汉式 双重检查
 * 此写法，支持延迟加载，线程安全，加锁粒度减小，并发性有一定提升。
 * 但是此方式可能存在指令重排问题，要解决这个问题，需要给instance成员变量加上volatile关键字，禁止指令重排序才行。
 *      实际上，只有很低版本的jdk才会有这个问题。现在的高版本jdk内部已经解决了这个问题（解决方案是，只要把对象new操作和初始化操作设计为原子操作即解决）
 * 同时也存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton4 implements Singleton, Serializable,Cloneable {

    private static Singleton4 instance;

    public static Singleton4 getInstance(){
        if(instance == null){
            synchronized (Singleton4.class){
                if(instance == null){
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }

    private Singleton4() {
        System.out.println("Singleton4....");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
