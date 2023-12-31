package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc: 课程抽象工厂
 * @Date: 2023/12/31 15:32
 * @Auther: dengyanliang
 */
public abstract class CourseFactory {
    public abstract Video getVideo();
    public abstract Article getArticle();
}
