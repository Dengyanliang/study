package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc:
 * @Date: 2023/12/31 15:38
 * @Auther: dengyanliang
 */
public class PythonVideo extends Video{
    @Override
    public void produce() {
        System.out.println("录制Python视频");
    }
}
