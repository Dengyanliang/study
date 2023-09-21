package com.deng.study.java.thread;

import org.junit.Test;

/**
 * @Desc: 打印数字
 *  1）三个线程，从1-20交替打印
 *  2）三个线程交替，循环打印ABC 5次
 * @Auther: dengyanliang
 * @Date: 2023/9/21 20:14
 */
public class PrintNumberTest {


    @Test
    public void testPrint(){
        PrintABC printABC = new PrintABC(1);
        new Thread(() -> {
            printABC.print(1,2);
        }, "t1").start();

        new Thread(() -> {
            printABC.print(2,3);
        }, "t2").start();

        new Thread(() -> {
            printABC.print(3,1);
        }, "t3").start();
    }

    @Test
    public void testPrintCycle(){
        PrintABC printABC = new PrintABC(5,1);
        new Thread(() -> {
            printABC.printCycle("a",1,2);
        }, "t1").start();

        new Thread(() -> {
            printABC.printCycle("b",2,3);
        }, "t2").start();

        new Thread(() -> {
            printABC.printCycle("c",3,1);
        }, "t3").start();
    }


}


class PrintABC{
    private int loopNumber; // 循环次数

    private static final int NUM = 10;
    private static int count = 1;
    private int initialFlag; // 初始标记

    public PrintABC(int initialFlag) {
        this.initialFlag = initialFlag;
    }

    public PrintABC(int loopNumber, int initialFlag) {
        this.loopNumber = loopNumber;
        this.initialFlag = initialFlag;
    }

    /**
     * 从1打印到NUM，整个过程只执行一次
     *
     * @param currentFlag 当前标记
     * @param nextFlag  下一个标记
     */
    public void print(int currentFlag ,int nextFlag) {
        while(count <= NUM ){
            synchronized (PrintABC.class){
                while (currentFlag != initialFlag){
                    try {
                        PrintABC.class.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(count > NUM){
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "：" + count);
                count++;
                initialFlag = nextFlag;
                PrintABC.class.notifyAll();
            }
        }
    }

    public void printCycle(String str,int currentFlag ,int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (PrintABC.class){
                while (currentFlag != initialFlag){
                    try {
                        PrintABC.class.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "：" + str);
                initialFlag = nextFlag;
                PrintABC.class.notifyAll();
            }
        }
    }
}

