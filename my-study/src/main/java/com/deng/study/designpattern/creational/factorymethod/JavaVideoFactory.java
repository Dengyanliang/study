package com.deng.study.designpattern.creational.factorymethod;


/**
 * @Desc: 跟集合中的ArrayList等作用类似
 * @see java.util.ArrayList
 * @Date: 2023/12/31 14:42
 * @Auther: dengyanliang
 */
public class JavaVideoFactory extends VideoFactory{
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }
}
