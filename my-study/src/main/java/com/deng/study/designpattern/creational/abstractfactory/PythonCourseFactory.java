package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc: Python课程工厂
 * @Date: 2023/12/31 15:36
 * @Auther: dengyanliang
 */
public class PythonCourseFactory extends CourseFactory{

    @Override
    public Video getVideo() {
        return new PythonVideo();
    }

    @Override
    public Article getArticle() {
        return new PythonArticle();
    }
}
