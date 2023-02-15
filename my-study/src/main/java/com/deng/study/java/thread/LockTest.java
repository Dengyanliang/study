package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/14 17:21
 */
@Slf4j
public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    private static void test2() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(()->{
            log.debug("尝试获取到锁");
            try {
                if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                    log.debug("没有获取到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获取到锁，出现异常");
                return;
            }

            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }
        },"t1");
        log.debug("lock begin..");
        lock.lock();
        log.debug("lock end...");

        t1.start();

        Thread.sleep(1000);
        log.debug("unlock begin..");
        lock.unlock();
        log.debug("unlock end..");
    }

    private static void test1() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(()->{
            log.debug("尝试获取到锁");
//                lock.lockInterruptibly(); // 可以被打断
            lock.lock(); // 不可以被打断

            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }
        },"t1");
        log.debug("lock begin..");
        lock.lock();
        log.debug("lock end...");

        t1.start();

        Thread.sleep(2000);
        log.debug("interrupt begin..");
        t1.interrupt();
        log.debug("interrupt end..");
    }
}
