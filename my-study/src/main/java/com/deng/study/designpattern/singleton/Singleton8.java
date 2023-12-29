package com.deng.study.designpattern.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法八、懒汉式 双重检查 + volatile
 * 同时防止单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton8 implements Singleton, Serializable,Cloneable {

    private static volatile Singleton8 instance;

    private static boolean firstCreate = true;

    public static Singleton8 getInstance(){
        if(instance == null){
            synchronized (Singleton8.class){
                if(instance == null){
                    instance = new Singleton8();
                }
            }
        }
        return instance;
    }

    /**
     * 防止单例被反射破坏
     */
    private Singleton8() {
        if(firstCreate){
            synchronized (Singleton8.class){
                if(firstCreate){
                    firstCreate = false;
                    System.out.println("Singleton8....");
                }
            }
        }else{
            throw new RuntimeException("单例模式无法被再次实例化");
        }
    }

    /**
     * 防止克隆破坏单例
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return instance;
    }

    /**
     * 防止反序列化破坏单例
     * @return
     */
    private Object readResolve(){
        System.out.println("----Singleton8 readResolve----");
        return instance;
    }

}
