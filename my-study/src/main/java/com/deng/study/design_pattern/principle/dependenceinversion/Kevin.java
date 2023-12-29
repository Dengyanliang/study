package com.deng.study.design_pattern.principle.dependenceinversion;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/28 17:14
 */
public class Kevin {

    /********* 第四版 begin set注入 *********/
    private IDICourse idiCourse;

    public void setIdiCourse(IDICourse idiCourse) {
        this.idiCourse = idiCourse;
    }

    public void studyImoocCourse(){
        idiCourse.studyCourse();
    }
    /********* 第四版 end *********/


//    /********* 第三版 begin 构造注入 *********/
//    private IDICourse idiCourse;
//
//    public Kevin(IDICourse idiCourse) {
//        this.idiCourse = idiCourse;
//    }
//
//    public void studyImoocCourse(){
//        idiCourse.studyCourse();
//    }
//    /********* 第三版 end *********/


    /********* 第二版 begin 方法传入 *********/
//    public void studyImoocCourse(IDICourse idiCourse){
//        idiCourse.studyCourse();
//    }
    /********* 第二版 end *********/

    /********* 第一版 begin 直接声明 *********/
//    public void studyJava(){
//        System.out.println("kevin 在学习Java");
//    }

//    public void studyPython(){
//        System.out.println("kevin 在学习Python");
//    }
    /******* 第一版 end *********/
}
