package com.deng.study.spring.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc:自定义注解
 * @Auther: dengyanliang
 * @Date: 2021/3/6 20:45
 */
@Retention(RetentionPolicy.RUNTIME) // 声明注解的保留期限
@Target(ElementType.METHOD)         // 声明注解的目标类型
public @interface MyAnnotation {    // 定义注解
    boolean value();                // 声明注解成员
}
