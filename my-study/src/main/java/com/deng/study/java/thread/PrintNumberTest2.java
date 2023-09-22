package com.deng.study.java.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 两个线程交替打印1-20之间的数字
 * MyThread1和MyThread2 是同一类，只能处理两个线程的场景，当有多个线程的时候，无法保证有序性
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/6 17:02
 */
public class PrintNumberTest2{

    public static void main(String[] args) {
        printByMyThread1();
    }

    private static void printByMyThread1(){
        Thread t1 = new MyThread1("t1");
        Thread t2 = new MyThread1("t2");
        t1.start();
        t2.start();
    }

    private static void printByMyThread2(){
        Thread t1 = new MyThread2("t1");
        Thread t2 = new MyThread2("t2");
        t1.start();
        t2.start();
    }
}


class MyThread1 extends Thread{
    private static final int NUM = 20;
    private static int count = 1;
    private String name;
    private static final Object objLock = new Object();

    public MyThread1(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (count <= NUM) {
            synchronized (objLock) {
                objLock.notifyAll();
                try {
                    if (count > NUM) {
                        break;
                    }
                    System.out.println(name + " num:" + count);
                    count++;
                    // 睡眠自己
                    objLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 唤醒对方
                objLock.notifyAll();
            }
        }
    }
}

class MyThread2 extends Thread{
    private static final int NUM = 20;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private String name;
    private static int count = 1;

    public MyThread2(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (count <= NUM) {
            lock.lock();
            try {
                // 唤醒对方
                condition.signalAll();

                if (count > NUM) {
                    break;
                }
                System.out.println(name + " num:" + count);
                count++;
                try {
                    // 睡眠自己
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 唤醒对方
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}


