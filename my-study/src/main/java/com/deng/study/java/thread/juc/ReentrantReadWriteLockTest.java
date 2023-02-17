package com.deng.study.java.thread.juc;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Desc: 读写锁自测
 * @Auther: dengyanliang
 * @Date: 2023/2/17 13:30
 */
public class ReentrantReadWriteLockTest {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(dataContainer::read, "t1").start();

        ThreadUtil.sleep(500);

        new Thread(dataContainer::write, "t2").start();
    }
}

@Slf4j
class DataContainer{
    private Object data;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public Object read(){
        log.debug("获取读锁....");
        readLock.lock();
        try {
            log.debug("读取数据");
            ThreadUtil.sleep(1000);
            return data;
        } finally {
            log.debug("释放读锁....");
            readLock.unlock();
        }
    }

    public void write(){
        log.debug("获取写锁....");
        writeLock.lock();
        log.debug("获取写锁成功...."); // keypoint 一定要等到读锁释放后，才能获取写锁成功
        try {
            log.debug("写入数据");
        } finally {
            log.debug("释放写锁....");
            writeLock.unlock();
        }
    }
}