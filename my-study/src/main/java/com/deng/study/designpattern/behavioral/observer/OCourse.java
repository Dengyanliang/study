package com.deng.study.designpattern.behavioral.observer;

import java.util.Observable;

/**
 * @Desc:
 * @Date: 2023/12/30 10:41
 * @Auther: dengyanliang
 */
public class OCourse extends Observable {
    private String courseName;

    public OCourse(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void produceQuestion(Question question){
        System.out.println(question.getUserName()+"在"+courseName+"课程中提交了一个问题");
        // 为了后面的通知
        setChanged();
        // 把问题通知出去
        notifyObservers(question);
    }

}
