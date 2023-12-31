package com.deng.study.designpattern.behavioral.templatemethod;

/**
 * @Desc:
 * @Date: 2023/12/30 10:25
 * @Auther: dengyanliang
 */
public class DesignPatternCourse extends ACourse{
    @Override
    protected void packageCourse() {
        System.out.println("提供课程源代码");
    }

    @Override
    protected boolean needWriteArticle() {
        return true;
    }
}
