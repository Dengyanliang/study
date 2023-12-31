package com.deng.study.designpattern.creational.factorymethod;

/**
 * @Desc:
 * @Date: 2023/12/31 14:47
 * @Auther: dengyanliang
 */
public class PythonVideoFactory extends VideoFactory{
    @Override
    public Video getVideo() {
        return new PythonVideo();
    }
}
