package com.deng.study.java.thread.juc;



import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc: 多线程打印变量值
 * 为什么需要用Sleep才能正确显示呢？？ // TODO
 * @Auther: dengyanliang
 * @Date: 2021/4/2 21:40
 */
public class ActomicIntegerTest {

    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
//        test();
//        test2();
//        test3();
        test4();
    }

    private static void test() throws InterruptedException {
        for(int j = 0; j < 10; j++){
            counter = 0;
            for(int i = 0 ; i < 10; i++){
                new Thread(() -> {
                    for(int k = 0 ; k < 1000; k++) {
                        counter++;
                    }
                }).start();
            }
            Thread.sleep(10);
            System.out.println("test count :"+counter);
        }
    }

    private static void test2() throws InterruptedException {
        Object object = new Object();
        for(int j = 0; j < 10; j++){
            counter = 0;
            for(int i = 0 ; i < 10; i++){
                new Thread(() -> {
                    for(int k = 0 ; k < 1000; k++) {
                        synchronized (object){
                            counter++;
                        }
                    }
                }).start();
            }
            Thread.sleep(10);
            System.out.println("test2() count :"+counter);
        }
    }

    private static void test3() throws InterruptedException {
//        A count = new ActomicInteger();

            Object object = new Object();
            for(int j = 0; j < 10; j++){
//            counter = 0;
                final AtomicInteger count = new AtomicInteger(0);
                for(int i = 0 ; i < 10; i++){
                    new Thread(() -> {
                        for(int k = 0 ; k < 1000; k++) {
                            synchronized (object){
                                count.incrementAndGet();
                            }
                        }
                    }).start();
                }
                Thread.sleep(10);
                System.out.println("test3() count :"+count);
            }
    }

    private static void test4() throws InterruptedException {
//        A count = new ActomicInteger();

        final AtomicInteger count = new AtomicInteger(0);
        for(int j = 0; j < 10; j++){

            Thread.sleep(10);
            System.out.println("test4 getAndIncrement :"+count.getAndIncrement());
        }
    }


}

