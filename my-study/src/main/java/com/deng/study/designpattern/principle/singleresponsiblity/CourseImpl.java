package com.deng.study.designpattern.principle.singleresponsiblity;

/**
 * @Auther: yanliangdeng
 * @Date: 2023/12/28 22:34
 * @Description:
 */
public class CourseImpl implements ICourseManager,ICourseContent{
    @Override
    public String getCourseName() {
        return null;
    }

    @Override
    public byte[] getCourseVideo() {
        return new byte[0];
    }

    @Override
    public void studyCourse() {

    }

    @Override
    public void refundCourse() {

    }
}
