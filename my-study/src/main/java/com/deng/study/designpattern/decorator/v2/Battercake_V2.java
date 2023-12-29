package com.deng.study.designpattern.decorator.v2;

/**
 * @Desc: 被装饰者需要继承公共抽象类
 * @Date: 2023/12/29 16:31
 * @Auther: dengyanliang
 */
public class Battercake_V2 extends ABattercake{

    protected String getDesc(){
        return "煎饼";
    }
    protected int cost(){
        return 8;
    }
}
