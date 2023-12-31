package com.deng.study.designpattern.creational.singleton;

/**
 * @Desc: 单例写法六、枚举方式
 * 此写法，线程安全，不能延迟加载
 * 由于枚举本身的特性，防止单例被反序列化、克隆破坏和反射破坏
 * @Auther: dengyanliang
 * @Date: 2023/9/19 10:43
 */
public enum  Singleton6 {

    instance ;

    Singleton6() {
        System.out.println("Singleton6....");
    }

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}
