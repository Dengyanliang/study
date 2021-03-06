package com.deng.study.spring.aop;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/6 20:53
 */
public class ForumService {

    @MyAnnotation(value = true)
    public void deleteForum(){
        System.out.println("delete fourm");
    }

    @MyAnnotation(value = false)
    public void deleteTopic(){
        System.out.println("delete topic");
    }

    private void test1(){

    }

    protected void test1(String name){

    }
}
