package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc:
 * @Date: 2023/12/31 15:37
 * @Auther: dengyanliang
 */
public class JavaVideo extends Video{
    @Override
    public void produce() {
        System.out.println("录制java视频");
    }
}
