package com.deng.study.designpattern.creational.factorymethod;

/**
 * @Desc: 跟ArrayList中内部类Itr 作用类似
 * @Date: 2023/12/31 14:14
 * @Auther: dengyanliang
 */
public class JavaVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制java视频");
    }
}
