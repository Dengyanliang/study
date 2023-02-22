package com.deng.study.java.thread.juc;

import com.deng.study.util.ThreadUtil;

import java.util.concurrent.locks.StampedLock;

/**
 * @Desc: 邮戳锁
 * @Auther: dengyanliang
 * @Date: 2023/2/20 20:55
 */
public class StampedLockTest {

    private static int number = 37;
    private static StampedLock stampedLock = new StampedLock();

    private static void write(){
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + " " + "写线程准备修改数据");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlock(stamp);
        }
        System.out.println(Thread.currentThread().getName() + " " + "写线程修改数据结束");
    }


    private static void read(){
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + " " + "读线程获取到读锁");
        for (int i = 0; i < 4; i++) {
            ThreadUtil.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " " + "正在读取中...");
        }
        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + " " + "获取到成员变量result:" + result);
            System.out.println("写线程没有修改成功，有读锁存在的时候，写锁无法介入，传统的读写互斥");
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    /**
     * 乐观读
     */
    private static void tryOptimisticRead(){
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        System.out.println("4s前stamp值 " + stampedLock.validate(stamp));
        for (int i = 1; i <= 4; i++) {
            System.out.println("等待。。。");
            ThreadUtil.sleep(1000);
            System.out.println("等待结束。。。");
            System.out.println(Thread.currentThread().getName() + "，result："+ result + "，正在读取 " + i + "秒后 stamp值 " + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改过---有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观锁升级为悲观锁");
                result = number;
                System.out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + " " + "最终值：" + result);
    }

    public static void main(String[] args) {
//        new Thread(() -> {
//            read();
//        }, "readLock").start();
//
//        ThreadUtil.sleep(1000);
//
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " " + "---come in");
//            write();
//        }, "writeLock").start();
//
//        ThreadUtil.sleep(5000);
//        System.out.println(Thread.currentThread().getName() + " " + "number：" + number);

        new Thread(() -> {
            tryOptimisticRead();
        }, "readLock").start();

        ThreadUtil.sleep(2000);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + "---come in");
            write();
        }, "writeLock").start();
    }
}
