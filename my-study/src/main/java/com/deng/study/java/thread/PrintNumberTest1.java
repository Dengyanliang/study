package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 三个线程交替打印abc 循环n次
 * @Auther: dengyanliang
 * @Date: 2023/2/14 20:42
 */
@Slf4j
public class PrintNumberTest1 {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        printABC();
    }

    /**
     * 循环打印abc n次
     */
    private static void printABC(){
        printABCByWaitNotify();
//        printABCByAwaitSignal();
//        printABCByParkUnpark();
    }

    /**
     * 需要声明三个静态Thread变量，不太好
     */
    static Thread t1;
    static Thread t2;
    static Thread t3;
    private static void printABCByParkUnpark(){
        PrintABCByParkUnpark printABC = new PrintABCByParkUnpark(5);
        t1 = new Thread(() -> {
            printABC.print("a", t2);
        });
        t2 = new Thread(() -> {
            printABC.print("b", t3);
        });
        t3 = new Thread(() -> {
            printABC.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }

    /**
     * 主线程中需要等待1s，不太友好
     */
    private static void printABCByAwaitSignal(){
        PrintABCByAwaitSignal printABC = new PrintABCByAwaitSignal(5);
        Condition a = printABC.newCondition();
        Condition b = printABC.newCondition();
        Condition c = printABC.newCondition();

        new Thread(() -> {
            printABC.print("a", a, b);
        }, "t1").start();
        new Thread(() -> {
            printABC.print("b", b, c);
        }, "t2").start();
        new Thread(() -> {
            printABC.print("c", c, a);
        }, "t3").start();

//        try {
//            Thread.sleep(1000); // 让 t1 t2 t3 三个线程都进入等待状态
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        printABC.lock();    // 主线程加锁
        a.signal();         // t1线程唤醒 keypoint 必须有在monitor块中，不然会报错java.lang.IllegalMonitorStateException。所以主线程加锁解锁必须存在
        printABC.unlock();  // 主线程解锁
    }

    /**
     * 不需要额外的条件，常用这种办法
     */
    private static void printABCByWaitNotify(){
        PrintABCByWaitNotify printABC = new PrintABCByWaitNotify(1, 5);

        new Thread(() -> {
            printABC.print("a", 1, 2);
        }, "t1").start();
        new Thread(() -> {
            printABC.print("b", 2, 3);
        }, "t2").start();
        new Thread(() -> {
            printABC.print("c", 3, 1);
        }, "t3").start();
    }


    /**
     * 打印一次 2 1
     */
    private static void test2And1(){
        testByParkUnPark();
//        testByWaitNotify();
    }

    /**
     * 使用park unPark 打印
     * 打印一次 2 1
     */
    private static void testByParkUnPark() {
        Thread t1 = new Thread(()->{
                log.debug("1 park");
                LockSupport.park();
                log.debug("1");
//            System.out.println("1");
        },"t1");

        Thread t2 = new Thread(()->{
            log.debug("2");
//            System.out.println("2");
            LockSupport.unpark(t1);
        },"t2");

        t1.start();
        t2.start();
    }


    /**
     * 使用wait notify 打印
     * 打印一次 2 1
     */
    private static void testByWaitNotify() {
        // flag变量，作为循环的条件，如果t2先notify时，此时t1尚未进入synchronized代码块，如果没有该条件，则wait时，会一直等待下去
        AtomicBoolean flag = new AtomicBoolean(false);
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(3000); // 为了测试t2先notify，t1后wait
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){
                try {
                    while (!flag.get()){
                        lock.wait();
                    }
                    log.debug("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (lock){
                log.debug("2");
                flag.set(true);
                lock.notify();
            }
        },"t2");

        t1.start();
        t2.start();
    }
}

class PrintABCByWaitNotify{
    private int flag; // 等待标记
    private int loopNumber; // 循环次数

    public PrintABCByWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     * str waitFlag nextFlag
     *  a     1       2
     *  b     2       3
     *  c     3       1
     */
    public void print(String str,int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                // 唤醒别人
                this.notifyAll();
                while (flag != waitFlag){
                    try {
                        // 睡眠自己
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "," + str);
                flag = nextFlag;
                // 唤醒所有线程，防止最后线程永远等待
                this.notifyAll();
            }
        }
    }
}

class PrintABCByAwaitSignal extends ReentrantLock {

    private int loopNumber;

    public PrintABCByAwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     * 打印5次 abc
     * @param str 要打印的内容
     * @param current 当前符合的条件值
     * @param next 下一个符合的条件值
     */
    public void print(String str, Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();  // 当前线程等待
                System.out.print(str);
                next.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

class PrintABCByParkUnpark {
    private int loopNuber;

    public PrintABCByParkUnpark(int loopNuber) {
        this.loopNuber = loopNuber;
    }
    public void print(String str,Thread next){
        for (int i = 0; i < loopNuber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }
}

