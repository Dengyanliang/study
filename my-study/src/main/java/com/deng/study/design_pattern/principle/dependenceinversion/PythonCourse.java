package com.deng.study.design_pattern.principle.dependenceinversion;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/28 17:21
 */
public class PythonCourse implements IDICourse{

    @Override
    public void studyCourse() {
        System.out.println("kevin 在学习Python");
    }
}
