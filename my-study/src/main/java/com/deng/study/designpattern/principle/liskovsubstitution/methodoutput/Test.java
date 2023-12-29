package com.deng.study.designpattern.principle.liskovsubstitution.methodoutput;

/**
 * @Desc:
 * @Date: 2023/12/29 12:00
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.method());
    }
}
