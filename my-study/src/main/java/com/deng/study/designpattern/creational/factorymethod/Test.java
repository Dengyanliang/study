package com.deng.study.designpattern.creational.factorymethod;

/**
 * @Desc:
 * @Date: 2023/12/31 14:38
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        JavaVideoFactory videoFactory = new JavaVideoFactory();
        Video video = videoFactory.getVideo();
        video.produce();
    }
}
