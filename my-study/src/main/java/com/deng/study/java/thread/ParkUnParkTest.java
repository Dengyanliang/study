package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Desc: park unPark
 * @Auther: dengyanliang
 * @Date: 2023/2/14 12:48
 */
@Slf4j
public class ParkUnParkTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("start...");
//            MyThreadUtil.sleep(20);
            log.debug("park 开始...");
            LockSupport.park();
            log.debug("park 结束...");
        });

        t1.start();
//        Thread.sleep(1000);

        log.debug("begin unpark...");
        LockSupport.unpark(t1); // 先执行unpark，还会继续执行park
        log.debug("end unpark...");
    }
}
