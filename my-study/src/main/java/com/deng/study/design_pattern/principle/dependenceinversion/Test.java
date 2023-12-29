package com.deng.study.design_pattern.principle.dependenceinversion;

/**
 * @Desc: 依赖导致原则
 * @Auther: dengyanliang
 * @Date: 2023/12/28 17:18
 */
public class Test {
    public static void main(String[] args) {
        /********* 第一版 *********/
//        Kevin kevin = new Kevin();
//        kevin.studyJava();
//        kevin.studyPython();

        /******* 第二版 *********/
//        Kevin kevin = new Kevin();
//        kevin.studyImoocCourse(new JavaCourse());
//        kevin.studyImoocCourse(new PythonCourse());

        /******* 第三版 *********/
        // 这里通过构造方法的传入，不是很方便
//        Kevin kevin = new Kevin(new JavaCourse());
//        kevin.studyImoocCourse();


        /******* 第四版 *********/
        Kevin kevin = new Kevin();
        kevin.setIdiCourse(new JavaCourse());
        kevin.studyImoocCourse();

        kevin.setIdiCourse(new PythonCourse());
        kevin.studyImoocCourse();

    }
}
