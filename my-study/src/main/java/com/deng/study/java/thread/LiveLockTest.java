package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc: 活锁问题，是两个线程相互改变对方的值导致的
 * @Auther: dengyanliang
 * @Date: 2023/2/14 16:30
 */
@Slf4j
public class LiveLockTest {
    private static volatile int count = 10;

    public static void main(String[] args) {
        new Thread(()->{
            while (count > 0){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                log.debug("count：{}",count);
            }
        },"t1").start();
        new Thread(()->{
            while (count < 20){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                log.debug("count：{}",count);
            }
        },"t2").start();
    }
}
