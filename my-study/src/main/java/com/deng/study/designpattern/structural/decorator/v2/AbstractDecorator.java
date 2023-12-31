package com.deng.study.designpattern.structural.decorator.v2;

/**
 * @Desc: 抽象的装饰者也需要继承公共抽象类
 * @Date: 2023/12/29 16:32
 * @Auther: dengyanliang
 */
public class AbstractDecorator extends ABattercake{

    private ABattercake aBattercake;

    public AbstractDecorator(ABattercake aBattercake) {
        this.aBattercake = aBattercake;
    }

    @Override
    protected String getDesc() {
        return aBattercake.getDesc();
    }

    @Override
    protected int cost() {
        return aBattercake.cost();
    }
}
