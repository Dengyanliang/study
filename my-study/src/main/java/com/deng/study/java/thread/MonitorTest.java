package com.deng.study.java.thread;

/**
 * @Desc: 主要是为了分析字节码
 * @Auther: dengyanliang
 * @Date: 2023/2/15 14:43
 */
public class MonitorTest {

    static final Object lock = new Object();
    static int counter = 0;

    public static void main(String[] args) {
        synchronized (lock){
            counter++;
        }
    }
}
