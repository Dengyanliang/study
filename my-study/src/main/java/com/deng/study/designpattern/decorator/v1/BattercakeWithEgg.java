package com.deng.study.designpattern.decorator.v1;

/**
 * @Desc: 加蛋煎饼
 * @Date: 2023/12/29 16:16
 * @Auther: dengyanliang
 */
public class BattercakeWithEgg extends Battercake_V1 {
    @Override
    public String getDesc() {
        return super.getDesc() + " 加一个鸡蛋";
    }

    @Override
    public int cost() {
        return super.cost() + 1;
    }
}
