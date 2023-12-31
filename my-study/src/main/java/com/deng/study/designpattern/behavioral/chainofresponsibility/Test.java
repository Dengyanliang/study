package com.deng.study.designpattern.behavioral.chainofresponsibility;

/**
 * @Desc:
 * @Date: 2023/12/30 11:31
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        CourseNameApprover courseNameApprover = new CourseNameApprover();
        ArticleApprover articleApprover = new ArticleApprover();
        VedioApprover vedioApprover = new VedioApprover();

        // 构造责任链
        courseNameApprover.setNextApprover(articleApprover);
        articleApprover.setNextApprover(vedioApprover);

        CRCourse course = new CRCourse();
        course.setName("Java");
        course.setArticle("Java手记");
        course.setVideo("Java视频");

        courseNameApprover.deploy(course);
    }
}
