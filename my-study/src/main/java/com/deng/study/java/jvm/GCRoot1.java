package com.deng.study.java.jvm;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2023-01-07 20:08:54
 */
public class GCRoot1 {
    // 2、方法区类的静态成员引用的对象
    public static Rumenz r2;
    // 3、方法区常量引用的对象
    public static final Rumenz r3 = new Rumenz();

    public static void main(String[] args) {
        // 1、虚拟机栈中引用的对象
        Rumenz r1 = new Rumenz();
        r1 = null;
    }
}
