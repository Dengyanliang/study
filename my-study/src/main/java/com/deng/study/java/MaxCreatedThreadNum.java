package com.deng.study.java;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: 统计当前系统与JVM最大可创建的线程数
 * @Auther: dengyanliang
 * @Date: 2023/9/20 17:46
 */
public class MaxCreatedThreadNum {

    private static final Object object = new Object();

    private static int count = 0;

    public static void main(String[] args) {
        while (true){
            new Thread(() -> {
                synchronized (object){
                    count++;
                    System.out.println("Thread #" + count);
                }
                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t1").start();
        }
    }
}
