package com.deng.study.designpattern.behavioral.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @Desc:
 * @Date: 2023/12/30 10:45
 * @Auther: dengyanliang
 */
public class Teacher implements Observer {
    private String teacherName;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     *
     * @param o    被观察者，这里是OCourse对象
     * @param arg  这里是Question对象
     */
    @Override
    public void update(Observable o, Object arg) {
        OCourse course = (OCourse)o;
        Question question = (Question)arg;
        System.out.println(teacherName+"老师的"+course.getCourseName()+"课程接收到"
                +question.getUserName()+"提交的问题："+question.getQuestionContent());
    }
}
