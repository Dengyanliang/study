package com.deng.study.java.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 打印数字
 *  1）三个线程，从1-20交替打印
 *  2）三个线程交替，循环打印ABC 5次
 * @Auther: dengyanliang
 * @Date: 2023/9/21 20:14
 */
public class PrintNumberTest {

    @Test
    public void printNumberByWaitNotify(){
        PrintByWaitNotify printByWaitNotify = new PrintByWaitNotify(1);
        new Thread(() -> {
            printByWaitNotify.printNumber(1,2);
        }, "t1").start();
        new Thread(() -> {
            printByWaitNotify.printNumber(2,3);
        }, "t2").start();
        new Thread(() -> {
            printByWaitNotify.printNumber(3,1);
        }, "t3").start();
    }

    @Test
    public void printNumberByAWaitSignal(){
        PrintByAwaitSignal printByAwaitSignal = new PrintByAwaitSignal();
        Condition condition1 = printByAwaitSignal.newCondition();
        Condition condition2 = printByAwaitSignal.newCondition();
        Condition condition3 = printByAwaitSignal.newCondition();

        new Thread(() -> {
            printByAwaitSignal.printNumber(condition1,condition2);
        }, "t1").start();
        new Thread(() -> {
            printByAwaitSignal.printNumber(condition2,condition3);
        }, "t2").start();
        new Thread(() -> {
            printByAwaitSignal.printNumber(condition3,condition1);
        }, "t3").start();

        printByAwaitSignal.lock(); // 主线程加锁
        condition1.signal(); // 唤醒t1线程 keypoint 必须有在monitor块中，不然会报错java.lang.IllegalMonitorStateException。所以主线程加锁解锁必须存在
        printByAwaitSignal.unlock(); // 主线程解锁
    }

    /**
     * 需要声明三个静态Thread变量，不太好
     */
    static Thread t1, t2, t3;
    @Test
    public void printNumberByParkUnPark(){

        PrintByParkUnPark printByParkUnPark = new PrintByParkUnPark();

        t1 = new Thread(() -> {
            printByParkUnPark.printNumber(t2);
        }, "t1");

        t2 = new Thread(() -> {
            printByParkUnPark.printNumber(t3);
        }, "t2");

        t3 = new Thread(() -> {
            printByParkUnPark.printNumber(t1);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }

    @Test
    public void printStrByWaitNotify(){
        PrintByWaitNotify printByWaitNotify = new PrintByWaitNotify(1,3);
        new Thread(() -> {
            printByWaitNotify.printStr("a",1,2);
        }, "t1").start();
        new Thread(() -> {
            printByWaitNotify.printStr("b",2,3);
        }, "t2").start();
        new Thread(() -> {
            printByWaitNotify.printStr("c",3,1);
        }, "t3").start();
    }

    @Test
    public void printStrByAWaitSignal(){
        PrintByAwaitSignal printByAwaitSignal = new PrintByAwaitSignal(3);
        Condition condition1 = printByAwaitSignal.newCondition();
        Condition condition2 = printByAwaitSignal.newCondition();
        Condition condition3 = printByAwaitSignal.newCondition();

        new Thread(() -> {
            printByAwaitSignal.printStr("a",condition1,condition2);
        }, "t1").start();
        new Thread(() -> {
            printByAwaitSignal.printStr("b",condition2,condition3);
        }, "t2").start();
        new Thread(() -> {
            printByAwaitSignal.printStr("c",condition3,condition1);
        }, "t3").start();

        printByAwaitSignal.lock(); // 主线程加锁
        condition1.signal(); // 唤醒t1线程 keypoint 必须有在monitor块中，不然会报错java.lang.IllegalMonitorStateException。所以主线程加锁解锁必须存在
        printByAwaitSignal.unlock(); // 主线程解锁
    }

    @Test
    public void printStrByParkUnPark() {
        PrintByParkUnPark printByParkUnPark = new PrintByParkUnPark(3);

        t1 = new Thread(() -> {
            printByParkUnPark.printStr("a",t2);
        }, "t1");

        t2 = new Thread(() -> {
            printByParkUnPark.printStr("b",t3);
        }, "t2");

        t3 = new Thread(() -> {
            printByParkUnPark.printStr("c",t1);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}


class PrintByWaitNotify {
    private int loopNumber; // 循环打印的次数
    private int count = 1; // 每次要输出的值
    private static final int NUM = 10; // count要累计到最大值
    private int initialFlag; // 初始标记

    public PrintByWaitNotify(int initialFlag) {
        this.initialFlag = initialFlag;
    }

    public PrintByWaitNotify(int initialFlag, int loopNumber) {
        this.initialFlag = initialFlag;
        this.loopNumber = loopNumber;
    }

    public void printStr(String str, int currentFlag,int nextFlag){
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                while (currentFlag != initialFlag){
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+":"+str);
                initialFlag = nextFlag;
                this.notifyAll();
            }
        }
    }

    public void printNumber(int currentFlag,int nextFlag){
        while(count <= NUM){
            synchronized (this){
                while (currentFlag != initialFlag){
                    try {
                        this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(count > NUM){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+count);
                count++;
                initialFlag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

class PrintByAwaitSignal extends ReentrantLock{
    private int loopNumber;
    private int count = 1;
    private static final int NUM = 10;

    public PrintByAwaitSignal() {

    }

    public PrintByAwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void printNumber(Condition current, Condition next){
        while (count <= NUM){
            lock();
            try {
                current.await();
                if(count > NUM){
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ":" + count);
                count++;
                next.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

    public void printStr(String str,Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.println(Thread.currentThread().getName() + ":" + str);
                next.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

class PrintByParkUnPark {
    private int loopNumber;
    private int count = 1;
    private static final int NUM = 10;

    public PrintByParkUnPark() {
    }

    public PrintByParkUnPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void printNumber(Thread next){
        while (count <= NUM){
            LockSupport.park();
            if(count > NUM){
                break;
            }
            System.out.println(Thread.currentThread().getName() + ":" + count);
            count++;
            LockSupport.unpark(next);
        }
    }

    public void printStr(String str,Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+ ":" + str);
            LockSupport.unpark(next);
        }
    }

}