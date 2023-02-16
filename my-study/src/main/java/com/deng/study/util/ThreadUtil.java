package com.deng.study.util;

/**
 * @Desc: 线程工具类
 * @Auther: dengyanliang
 * @Date: 2023/2/15 20:44
 */
public class ThreadUtil {


    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
