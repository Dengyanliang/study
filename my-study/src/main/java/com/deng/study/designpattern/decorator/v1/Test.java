package com.deng.study.designpattern.decorator.v1;

/**
 * @Desc:
 * @Date: 2023/12/29 16:19
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Battercake_V1 battercake = new Battercake_V1();
        System.out.println(battercake.getDesc()+",价格："+battercake.cost());

        // 如果此时，客户要加两个鸡蛋，该怎么处理呢？？？
        BattercakeWithEgg battercakeWithEgg = new BattercakeWithEgg();
        System.out.println(battercakeWithEgg.getDesc()+",价格："+battercakeWithEgg.cost());

        BattercakeWithEggSausage battercakeWithEggSausage = new BattercakeWithEggSausage();
        System.out.println(battercakeWithEggSausage.getDesc()+",价格："+battercakeWithEggSausage.cost());
    }
}
