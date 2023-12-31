package com.deng.study.designpattern.structural.adapter.objectadapter;

/**
 * @Desc:
 * @Date: 2023/12/31 16:04
 * @Auther: dengyanliang
 */
public class Adapter implements Target{
    private Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        adaptee.apapteeRequest();
    }
}
