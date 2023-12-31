package com.deng.study.designpattern.structural.decorator.v2;

/**
 * @Desc:
 * @Date: 2023/12/29 16:37
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        ABattercake aBattercake = null;
        // Battercake_V2 是被装饰的对象
        aBattercake = new Battercake_V2();
        // EggDecorator是具体的装饰者，包装着被装饰的对象Battercake_V2
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new SausageDecorator(aBattercake);

        System.out.println(aBattercake.getDesc()+",价格：" + aBattercake.cost());

    }
}
