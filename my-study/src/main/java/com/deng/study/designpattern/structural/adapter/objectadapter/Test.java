package com.deng.study.designpattern.structural.adapter.objectadapter;


/**
 * @Desc:
 * @Date: 2023/12/31 16:00
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();

        System.out.println("------------------");

        Target adapterTarget = new Adapter();
        adapterTarget.request();
    }
}
