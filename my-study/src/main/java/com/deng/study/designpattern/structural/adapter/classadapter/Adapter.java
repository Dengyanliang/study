package com.deng.study.designpattern.structural.adapter.classadapter;

/**
 * @Desc:
 * @Date: 2023/12/31 15:59
 * @Auther: dengyanliang
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void request() {
        super.apapteeRequest();
    }
}
