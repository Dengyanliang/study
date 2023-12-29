package com.deng.study.design_pattern.principle.singleresponsiblity;

/**
 * @Auther: yanliangdeng
 * @Date: 2023/12/28 22:32
 * @Description:
 */
public interface ISRCourse {

    String getCourseName();
    byte[] getCourseVideo();

    void studyCourse();
    void refundCourse();
}
