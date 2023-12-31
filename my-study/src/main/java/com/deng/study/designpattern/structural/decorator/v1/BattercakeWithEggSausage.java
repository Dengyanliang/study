package com.deng.study.designpattern.structural.decorator.v1;

/**
 * @Desc: 加蛋加香肠煎饼
 * @Date: 2023/12/29 16:16
 * @Auther: dengyanliang
 */
public class BattercakeWithEggSausage extends BattercakeWithEgg{
    @Override
    public String getDesc() {
        return super.getDesc() + " 加一根香肠";
    }

    @Override
    public int cost() {
        return super.cost() + 2;
    }
}
