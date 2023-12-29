package com.deng.study.designpattern.principle.openclose;

/**
 * @Desc: 开闭原则
 * @Auther: dengyanliang
 * @Date: 2023/12/28 16:42
 */
public interface IOCCourse {

    Integer getId();

    String getName();
    // 假如这里有打折活动，是否应该在这个方法中增加一个打折方法？
    Double getPrice();
}
