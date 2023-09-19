package com.deng.study.design_pattern.singleton;

import java.io.Serializable;

/**
 * @Desc: 单例写法七、内部类方式
 * 此方法，支持延迟加载，线程安全。但同时存在单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public class Singleton7 implements Singleton, Serializable,Cloneable {


    private static class LazyHolder{
        private static final Singleton7 instance = new Singleton7();
    }

    public static Singleton7 getInstance(){
        return LazyHolder.instance;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Singleton7() {
        System.out.println("Singleton7....");
    }

}
