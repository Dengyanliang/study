package com.deng.study.designpattern.principle.dependenceinversion;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/28 17:21
 */
public class JavaCourse implements IDICourse{

    @Override
    public void studyCourse() {
        System.out.println("kevin 在学习Java");
    }
}
