package com.deng.study.designpattern.principle.liskovsubstitution.methodinput;

import java.util.HashMap;

/**
 * @Desc:
 * @Date: 2023/12/29 11:30
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Child child = new Child();
        HashMap<Object, Object> hashMap = new HashMap<>();
        child.method(hashMap);
    }
}
