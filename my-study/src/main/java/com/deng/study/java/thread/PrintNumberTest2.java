package com.deng.study.java.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 两个或者两个以上线程交替打印1-20之间的数字
 * 目前写了四种方法，归为两类：
 *      MyThread1和MyThread2 是同一类，只能处理两个线程的场景，当有多个线程的时候，无法保证有序性
 *      MyThread3和MyThread4是同一类，可以处理多个线程的场景。
 * 就使用简洁度而言，第四种方式最方便，也是我比较推崇。
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/6 17:02
 */
public class PrintNumberTest2{


    @Test
    public void printByMyThread1(){
        Thread t1 = new MyThread1("t1");
        Thread t2 = new MyThread1("t2");
        t1.start();
        t2.start();
    }

    @Test
    public void printByMyThread2(){
        Thread t1 = new MyThread2("t1");
        Thread t2 = new MyThread2("t2");
        t1.start();
        t2.start();
    }

    @Test
    public void printByAwaitSignal(){
        PrintByAwaitSignal printByAwaitSignal = new PrintByAwaitSignal();
        Condition condition1 = printByAwaitSignal.newCondition();
        Condition condition2 = printByAwaitSignal.newCondition();
        Condition condition3 = printByAwaitSignal.newCondition();
        Condition condition4 = printByAwaitSignal.newCondition();
        new Thread(()->{
            printByAwaitSignal.print(condition1,condition2);
        },"t1").start();
        new Thread(()->{
            printByAwaitSignal.print(condition2,condition3);
        },"t2").start();
        new Thread(()->{
            printByAwaitSignal.print(condition3,condition4);
        },"t3").start();
        new Thread(()->{
            printByAwaitSignal.print(condition4,condition1);
        },"t4").start();


        printByAwaitSignal.lock();
        condition1.signal();
        printByAwaitSignal.unlock();
    }

    @Test
    public void printByWaitNotify(){
        PrintByWaitNotify printByWaitNotify = new PrintByWaitNotify(1);

        new Thread(() -> {
            printByWaitNotify.print(1, 2);
        }, "t1").start();
        new Thread(() -> {
            printByWaitNotify.print(2, 3);
        }, "t2").start();
        new Thread(() -> {
            printByWaitNotify.print(3, 4);
        }, "t3").start();
        new Thread(() -> {
            printByWaitNotify.print(4, 1);
        }, "t4").start();
    }
}


class MyThread1 extends Thread{
    private static final int NUM = 20;
    private static int count = 1;
    private String name;

    public MyThread1(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (count <= NUM) {
            synchronized (MyThread1.class) {
                if (count > NUM) {
                    break;
                }
                System.out.println(name + " num:" + count);
                count++;

                // 唤醒对方
                MyThread1.class.notifyAll();
                try {
                    // 睡眠自己
                    MyThread1.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 唤醒对方
//                MyThread1.class.notifyAll();
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
                if (count > NUM) {
                    break;
                }

                System.out.println(name + " num:" + count);
                count++;

                // 唤醒对方
                condition.signalAll();

                try {
                    // 睡眠自己
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 唤醒对方
//                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

class PrintByAwaitSignal extends ReentrantLock{
    private static final int NUM = 20;
    private static int count = 1;

    public PrintByAwaitSignal(){
    }

    public void print(Condition current,Condition next)  {
        while(count <= NUM){
            lock();
            try {
                current.await();
                if(count > NUM){
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " num:" + count);
                count++;
                next.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

class PrintByWaitNotify {
    private static final int NUM = 20;
    private static int count = 1;
    private int flag; // 等待标记

    public PrintByWaitNotify(int flag){
        this.flag = flag;
    }

    public void print(int waitFlag,int nextFlag)  {
        while(count <= NUM){
            synchronized (this){
                while(waitFlag != flag){
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(count > NUM){
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " num:" + count);
                count++;
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

