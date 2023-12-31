package com.deng.study.designpattern.creational.factorymethod;

/**
 * @Desc:
 * @Date: 2023/12/31 14:14
 * @Auther: dengyanliang
 */
public class PythonVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制Python视频");
    }
}
