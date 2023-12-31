package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc: Java课程工厂
 * @Date: 2023/12/31 15:36
 * @Auther: dengyanliang
 */
public class JavaCourseFactory extends CourseFactory{

    @Override
    public Video getVideo() {
        return new JavaVideo();
    }

    @Override
    public Article getArticle() {
        return new JavaArticle();
    }
}
