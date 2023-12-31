package com.deng.study.designpattern.principle.singleresponsiblity;

/**
 * @Desc: 单一职责在实际项目中使用的比较少。接口和方法一定要做到单一职责，类不用遵循这个原则
 * @Auther: dengyanliang
 * @Date: 2023/12/28 22:27
 */
public class Test {
    public static void main(String[] args) {
        // v1 版本
//        Bird bird = new Bird();
//        bird.mainMoveMode("鸵鸟");
//        bird.mainMoveMode("大雁");

        FlyBird flyBird = new FlyBird();
        flyBird.mainMoveMode("大雁");

        WalkBird walkBird = new WalkBird();
        walkBird.mainMoveMode("鸵鸟");


    }
}
