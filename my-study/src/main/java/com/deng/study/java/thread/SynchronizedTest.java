package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/22 11:09
 */
public class SynchronizedTest {

    private static Object lock = new Object();

    public static void main(String[] args) {
        偏向锁测试();
    }

    /**
     * 锁的value值介绍
     */
    private static void synchronizedValue(){
        Object o = new Object();

        System.out.println("10进制："+o.hashCode());
        System.out.println("16进制："+Integer.toHexString(o.hashCode()));
        System.out.println("2进制："+Integer.toBinaryString(o.hashCode()));

        // 2进制：10001000000101000001101000111
        //       10001000000101000001101000111
        // 锁标志位可以打印出101，表示偏向锁
        System.out.println(ClassLayout.parseInstance(o).toPrintable()); // 0     4        (object header)   05 00 00 00 (00000101 00000000 00000000 00000000) (5)
    }


    private static void 偏向锁测试(){
        // KEYPOINT 这里要延迟5秒启动的原因，是因为偏向锁是默认延迟的，不会在程序启动时立即生效，JVM默认配置的-XX:BiasedLockingStartupDelay=4 默认是4秒
        MyThreadUtil.sleep(5000);
        Object o = new Object();
        System.out.println(Integer.toHexString(o.hashCode()));
        System.out.println(Integer.toBinaryString(o.hashCode()));
        //    10001000000101000001101000111
        // 00010001000000101000001101000111

        // 锁标志位可以打印出101，表示偏向锁
        System.out.println(ClassLayout.parseInstance(o).toPrintable()); // 0     4        (object header)   05 00 00 00 (00000101 00000000 00000000 00000000) (5)
        System.out.println("----------------------");
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 轻量锁的标志位为 00
     */
    private static void 轻量级锁测试(){
        new Thread(() -> {
            synchronized (lock){
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }, "t1").start();
    }

    /**
     * 重量锁的标志位为 10
     */
    private static void 重量级锁测试(){
        new Thread(() -> {
            synchronized (lock){
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (lock){
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }, "t2").start();
    }


    private static void printNumber() {
        Number number1 = new Number();
        Number number2 = new Number();
        Number number3 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
}

class Number {
    public synchronized static void getOne(){
        System.out.println("one");
    }
    public synchronized void getTwo(){
        System.out.println("two");
    }

    public void getThree(){
        System.out.println("three");
    }
}
