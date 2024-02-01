package com.deng.study.designpattern.behavioral.chainofresponsibility.one;

/**
 * @Desc: 批准者
 * @Date: 2023/12/30 11:21
 * @Auther: dengyanliang
 */
public abstract class Approver {
    // keypoint：一定要声明一个当前类类型的属性，这个跟装饰器模式中的装饰者类似
    protected Approver approver;

    /**
     * 设置下一个处理者
     * @param approver
     */
    public void setNextApprover(Approver approver){
        this.approver = approver;
    }

    public abstract void deploy(CRCourse order);
}
