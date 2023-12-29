package com.deng.study.designpattern.decorator.v2;

/**
 * @Desc: 香肠装饰者类
 * @Date: 2023/12/29 16:34
 * @Auther: dengyanliang
 */
public class SausageDecorator extends AbstractDecorator{
    public SausageDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc() + " 加一根香肠";
    }

    @Override
    protected int cost() {
        return super.cost() + 2;
    }
}
