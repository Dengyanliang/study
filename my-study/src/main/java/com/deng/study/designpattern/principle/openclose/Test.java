package com.deng.study.designpattern.principle.openclose;

/**
 * @Desc: 开闭原则测试
 * @Auther: dengyanliang
 * @Date: 2023/12/28 16:44
 */
public class Test {

    public static void main(String[] args) {
        IOCCourse javaCourse = new JavaCourse(100,"java",518d);
        System.out.println(javaCourse.getId()+","+javaCourse.getName()+","+javaCourse.getPrice());

        //
        IOCCourse iCourse = new JavaDiscountCourse(100,"java",518d);
        JavaDiscountCourse javaDiscountCourse = (JavaDiscountCourse)iCourse;
        System.out.println(javaDiscountCourse.getId()+","+javaDiscountCourse.getName()+","+javaDiscountCourse.getPrice()+"," + javaDiscountCourse.getOriginPrice());

    }
}
