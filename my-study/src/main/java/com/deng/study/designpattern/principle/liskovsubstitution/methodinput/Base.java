package com.deng.study.designpattern.principle.liskovsubstitution.methodinput;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2023/12/29 11:25
 * @Auther: dengyanliang
 */
public class Base {
    public void method_V1(HashMap map){
        System.out.println("父类被执行");
    }

    public void method(Map map){
        System.out.println("父类被执行");
    }
}
