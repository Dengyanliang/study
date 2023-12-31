package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc:
 * @Date: 2023/12/31 15:41
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        CourseFactory courseFactory = new JavaCourseFactory();
        Video video = courseFactory.getVideo();
        Article article = courseFactory.getArticle();
        video.produce();
        article.produce();
    }
}
