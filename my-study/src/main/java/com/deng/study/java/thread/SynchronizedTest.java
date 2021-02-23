package com.deng.study.java.thread;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/22 11:09
 */
public class SynchronizedTest {

    public static void main(String[] args) {
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
