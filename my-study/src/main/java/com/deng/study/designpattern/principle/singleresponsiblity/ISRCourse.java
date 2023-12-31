package com.deng.study.designpattern.principle.singleresponsiblity;

/**
 * @Auther: dengyanliang
 * @Date: 2023/12/28 22:32
 * @Desc:
 */
public interface ISRCourse {

    String getCourseName();
    byte[] getCourseVideo();

    void studyCourse();
    void refundCourse();
}
