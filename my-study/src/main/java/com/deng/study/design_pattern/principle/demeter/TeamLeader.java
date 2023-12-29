package com.deng.study.design_pattern.principle.demeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: yanliangdeng
 * @Date: 2023/12/29 10:12
 * @Description:
 */
public class TeamLeader {
//    public void checkNumberOfCourse_V1(List<DCourse> courseList){
//        System.out.println("在线课程的数量是：" + courseList.size());
//    }

    public void checkNumberOfCourse(){
        List<DCourse> courseList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            courseList.add(new DCourse());
        }
        System.out.println("在线课程的数量是：" + courseList.size());
    }
}
