package com.deng.study.designpattern.structural.adapter.objectadapter;

/**
 * @Desc: 具体的目标实现类
 * @Date: 2023/12/31 15:58
 * @Auther: dengyanliang
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("具体的目标实现类");
    }
}
