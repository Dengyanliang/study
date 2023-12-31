package com.deng.study.designpattern.behavioral.templatemethod;

/**
 * @Desc:
 * @Date: 2023/12/30 10:25
 * @Auther: dengyanliang
 */
public class FECourse extends ACourse{
    // 开放给客户端，方便客户端自己选择
    private boolean needWriteArticleFlag = false;

    public FECourse() {

    }

    public FECourse(boolean needWriteArticleFlag) {
        this.needWriteArticleFlag = needWriteArticleFlag;
    }

    @Override
    protected void packageCourse() {
        System.out.println("提供课程的前端代码");
        System.out.println("提供课程的图片等多媒体素材");
    }

    @Override
    protected boolean needWriteArticle() {
        return needWriteArticleFlag;
    }
}
