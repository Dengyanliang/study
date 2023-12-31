package com.deng.study.designpattern.behavioral.templatemethod;

/**
 * @Desc:
 * @Date: 2023/12/30 10:27
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        ACourse designPatternCourse = new DesignPatternCourse();
        designPatternCourse.makeCourse();

        System.out.println("----------------------");

        FECourse feCourse = new FECourse();
        feCourse.makeCourse();
    }
}
