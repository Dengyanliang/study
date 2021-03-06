package com.deng.study.spring.aop;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/6 20:56
 */
public class AnnotationTest {
    public static void main(String[] args) {
        Class clazz = ForumService.class;
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println(methods.length); // 声明了几个方法
        for(Method method : methods){
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            if(Objects.nonNull(myAnnotation)){
                if(myAnnotation.value()){
                    System.out.println(method.getName() + "()需要测试");
                }else{
                    System.out.println(method.getName() + "()不需要测试");
                }
            }
        }
    }
}
