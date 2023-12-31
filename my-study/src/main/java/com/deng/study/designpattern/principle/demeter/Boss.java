package com.deng.study.designpattern.principle.demeter;

/**
 * @Auther: dengyanliang
 * @Date: 2023/12/29 10:12
 * @Desc:
 */
public class Boss {
    // 这里Boss类不应该知道DCourse类，而只应该和TeamLeader打交道
//    public void commandCheckNumber_V1(TeamLeader teamLeader){
//        List<DCourse> courseList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            courseList.add(new DCourse());
//        }
//        teamLeader.checkNumberOfCourse_V1(courseList);
//    }

    public void commandCheckNumber(TeamLeader teamLeader){
        teamLeader.checkNumberOfCourse();
    }
}
