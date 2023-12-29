package com.deng.study.designpattern.principle.liskovsubstitution.methodinput;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2023/12/29 11:29
 * @Auther: dengyanliang
 */
public class Child extends Base{

    /**
     * 如果传入的参数类型是HashMap，比Map范围小，则不会执行该方法，
     * 而会去执行Base方法中的method_V1方法，这样就会引起混乱
     *
     * @param map
     */
    public void method_V1(Map map) {
        System.out.println("子类Map入参方法被执行");
    }

    public void method(HashMap map) {
        System.out.println("子类HashMap入参方法被执行");
    }


}
