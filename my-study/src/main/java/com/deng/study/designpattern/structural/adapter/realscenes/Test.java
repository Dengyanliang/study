package com.deng.study.designpattern.structural.adapter.realscenes;

/**
 * @Desc:
 * @Date: 2023/12/31 16:44
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter();
        dc5.outputDC5V();
    }
}
