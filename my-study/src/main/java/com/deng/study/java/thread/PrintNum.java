package com.deng.study.java.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 两个或者两个以上线程交替打印1-100之间的数字
 * 目前写了四种方法，归为两类，MyThread1和MyThread2 是同一类，只能处理两个线程的场景，当有多个线程的时候，无法保证有序性
 * MyThread3和MyThread4是同一类，可以处理多个线程的场景。
 * 就使用简洁度而言，第四种方式最方便，也是我比较推崇。
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/6 17:02
 */
public class PrintNum{

    public static void main(String[] args) {

        printByMyThread1();
        printByMyThread2();

        printByMyThread3();
        printByMyThread4();

    }

    private static void printByMyThread1(){
        Thread t1 = new MyThread1("t1");
        Thread t2 = new MyThread1("t2");
        Thread t3 = new MyThread1("t3");
        t1.start();
        t2.start();
        t3.start();
    }

    private static void printByMyThread2(){
        Thread t1 = new MyThread2("t1");
        Thread t2 = new MyThread2("t2");
        t1.start();
        t2.start();
    }

    private static void printByMyThread3(){
        MyThread3 myThread3 = new MyThread3();
        Condition condition1 = myThread3.newCondition();
        Condition condition2 = myThread3.newCondition();
        Condition condition3 = myThread3.newCondition();
        Condition condition4 = myThread3.newCondition();
        new Thread(()->{
            myThread3.print(condition1,condition2);
        },"t1").start();
        new Thread(()->{
            myThread3.print(condition2,condition3);
        },"t2").start();
        new Thread(()->{
            myThread3.print(condition3,condition4);
        },"t3").start();
        new Thread(()->{
            myThread3.print(condition4,condition1);
        },"t4").start();


        myThread3.lock();
        condition1.signal();
        myThread3.unlock();
    }

    private static void printByMyThread4(){
        MyThread4 myThread4 = new MyThread4(1);

        new Thread(() -> {
            myThread4.print(1, 2);
        }, "t1").start();
        new Thread(() -> {
            myThread4.print(2, 3);
        }, "t2").start();
        new Thread(() -> {
            myThread4.print(3, 4);
        }, "t3").start();
        new Thread(() -> {
            myThread4.print(4, 1);
        }, "t4").start();
    }
}



class MyThread1 extends Thread{
    private static int cnt = 1;
    private String name;

    public MyThread1(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (cnt <= 100) {
            synchronized (MyThread1.class) {
                if (cnt > 100) {
                    break;
                }
                System.out.println(name + " num:" + cnt);
                cnt++;
                MyThread1.class.notifyAll();
                try {
                    MyThread1.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MyThread2 extends Thread{
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private String name;
    private static int cnt = 1;

    public MyThread2(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (cnt <= 100) {
            lock.lock();
            if (cnt > 100) {
                break;
            }
            System.out.println(name + " num:" + cnt);
            cnt++;
            condition.signal();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }
}

class MyThread3 extends ReentrantLock{
    private static int cnt = 1;

    public MyThread3(){
    }

    public void print(Condition current,Condition next)  {
        while(cnt <= 100){
            lock();
            try {
                current.await();
                if(cnt > 100){
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " num:" + cnt);
                cnt++;
                next.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

class MyThread4{
    private static int cnt = 1;
    private int flag; // 等待标记

    public MyThread4(int flag){
        this.flag = flag;
    }

    public void print(int waitFlag,int nextFlag)  {
        while(cnt <= 100){
            synchronized (this){
                while(waitFlag != flag){
                    try {
                        System.out.println(Thread.currentThread().getName() + " waiting..begin");
                        this.wait();
                        System.out.println(Thread.currentThread().getName() + " waiting..end");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(cnt > 100){
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " num:" + cnt);
                cnt++;
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

