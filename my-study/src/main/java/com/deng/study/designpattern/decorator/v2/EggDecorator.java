package com.deng.study.designpattern.decorator.v2;

/**
 * @Desc: 鸡蛋装饰者类
 * @Date: 2023/12/29 16:34
 * @Auther: dengyanliang
 */
public class EggDecorator extends AbstractDecorator{
    public EggDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc() + " 加一个鸡蛋";
    }

    @Override
    protected int cost() {
        return super.cost() + 1;
    }
}
