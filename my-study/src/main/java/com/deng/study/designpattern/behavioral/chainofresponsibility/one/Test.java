package com.deng.study.designpattern.behavioral.chainofresponsibility.one;

/**
 * @Desc: 责任链模式
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

        CRCourse order = new CRCourse();
        order.setName("Java");
        order.setArticle("Java手记");
        order.setVideo("Java视频");

        courseNameApprover.deploy(order);
    }
}
