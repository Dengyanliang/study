package com.deng.study.design_pattern.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法二、懒汉式
 * 此写法，支持延迟加载，但是存在线程安全问题，是错误写法，同时也存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton2 implements Singleton, Serializable,Cloneable {

    private static Singleton2 instance;

    public static Singleton2 getInstance(){
        if(instance == null){
            instance = new Singleton2();
        }
        return instance;
    }

    private Singleton2() {
        System.out.println("Singleton2....");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
