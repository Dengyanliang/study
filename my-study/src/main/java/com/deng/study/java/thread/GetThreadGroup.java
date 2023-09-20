package com.deng.study.java.thread;

/**
 * @Desc: 获取线程组
 *   java 启动main线程后，有几个线程组？
 *   有两个线程组：
 *      一、main线程组，里面有两个线程
 *      二、system线程组，里面有3个线程和一个线程组，它是main线程组的父线程组
 * @Auther: dengyanliang
 * @Date: 2023/9/20 17:32
 */
public class GetThreadGroup {


    public static void main(String[] args) {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println("threadGroup.getName()=" + threadGroup.getName()); // main
        System.out.println("threadGroup.getMaxPriority()=" + threadGroup.getMaxPriority()); // 10
        System.out.println("threadGroup.activeCount()=" + threadGroup.activeCount()); // 2
        threadGroup.list();

        System.out.println("-----------------------------");

        ThreadGroup parentThreadGroup = threadGroup.getParent();
        System.out.println("parentThreadGroup.getName()=" + parentThreadGroup.getName()); // system
        System.out.println("parentThreadGroup.getMaxPriority()=" + parentThreadGroup.getMaxPriority()); // 10
        System.out.println("parentThreadGroup.activeCount()=" + parentThreadGroup.activeCount()); // 5
        System.out.println("parentThreadGroup.getParent()=" + parentThreadGroup.getParent()); // null
        parentThreadGroup.list();


    }
}
