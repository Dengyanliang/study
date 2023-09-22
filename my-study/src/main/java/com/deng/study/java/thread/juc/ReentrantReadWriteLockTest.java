package com.deng.study.java.thread.juc;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Desc: 读写锁自测
 * @Auther: dengyanliang
 * @Date: 2023/2/17 13:30
 */
public class ReentrantReadWriteLockTest {
    private static final int SHARED_SHIFT   = 16;
    private static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    /** Returns the number of shared holds represented in count  */
    private static int sharedCount(int c) {
        return c >>> SHARED_SHIFT;
    }
    /** Returns the number of exclusive holds represented in count  */
    private static int exclusiveCount(int c) {
        return c & EXCLUSIVE_MASK;
    }


    public static void main(String[] args) {

        int exclusiveCount = exclusiveCount(2);
        System.out.println(exclusiveCount);

        int sharedCount = sharedCount(2);
        System.out.println(sharedCount);


//
        DataContainer dataContainer = new DataContainer();
        new Thread(dataContainer::read, "t1").start();
        new Thread(dataContainer::read, "t2").start();

        MyThreadUtil.sleep(500);

        new Thread(dataContainer::write, "t3").start();
        new Thread(dataContainer::write, "t4").start();
    }
}

@Slf4j
class DataContainer{
    private Object data;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public Object read(){
        log.debug("获取读锁中....");
        readLock.lock();
        log.debug("获取读锁成功....");
        try {
            log.debug("读取数据");
            MyThreadUtil.sleep(1000);
            return data;
        } finally {
            log.debug("释放读锁....");
            readLock.unlock();
        }
    }

    public void write(){
        log.debug("获取写锁中....");
        writeLock.lock();
        log.debug("获取写锁成功...."); // keypoint 一定要等到读锁释放后，才能获取写锁成功

        try {
            log.debug("写入数据");

            MyThreadUtil.sleep(200);
            log.debug("锁降级，获取读锁中....");
            readLock.lock();
            log.debug("锁降级，获取读锁成功");

        } finally {
            // 在锁降级的程序中，一般是按照先释放写锁，在释放读锁的步骤来的
            log.debug("释放写锁....");
            writeLock.unlock();

            log.debug("锁降级，释放读锁...");
            readLock.unlock();
        }
    }
}