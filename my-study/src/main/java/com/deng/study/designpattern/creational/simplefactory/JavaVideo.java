package com.deng.study.designpattern.creational.simplefactory;

/**
 * @Desc:
 * @Date: 2023/12/31 14:14
 * @Auther: dengyanliang
 */
public class JavaVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制java视频");
    }
}
