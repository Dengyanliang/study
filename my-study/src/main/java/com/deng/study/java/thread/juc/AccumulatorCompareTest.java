package com.deng.study.java.thread.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Desc: 四种计数器的性能比较
 *
 * 50个线程，每个线程计算1000万次，性能比较如下，可以看到LongAdder和LongAccumulator性能差不多
 * clickBySynchronized---costTime：20199毫秒，总数：500000000
 * clickByAtomicLong---costTime：1416毫秒，总数：50000000
 * clickByLongAdder---costTime：154毫秒，总数：50000000
 * clickByLongAccumulator---costTime：185毫秒，总数：50000000
 *
 * @Auther: dengyanliang
 * @Date: 2023/2/23 09:46
 */
public class AccumulatorCompareTest {
    public static final int LOOP_COUNT = 10000;
    public static final int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();

        clickBySynchronized(clickNumber);

        clickByAtomicLong(clickNumber);

        clickByLongAdder(clickNumber);

        clickByLongAccumulator(clickNumber);

    }

    private static void clickByLongAccumulator(ClickNumber clickNumber) throws InterruptedException {
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * LOOP_COUNT; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAccumulator---costTime：" + (endTime-startTime) + "毫秒，总数：" + clickNumber.longAccumulator.get());
    }

    private static void clickByLongAdder(ClickNumber clickNumber) throws InterruptedException {
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * LOOP_COUNT; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAdder---costTime：" + (endTime-startTime) + "毫秒，总数：" + clickNumber.longAdder.sum());
    }

    private static void clickByAtomicLong(ClickNumber clickNumber) throws InterruptedException {
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * LOOP_COUNT; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByAtomicLong---costTime：" + (endTime-startTime) + "毫秒，总数：" + clickNumber.atomicLong.get());
    }

    private static void clickBySynchronized(ClickNumber clickNumber) throws InterruptedException {
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000 * LOOP_COUNT; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickBySynchronized---costTime：" + (endTime-startTime) + "毫秒，总数：" + clickNumber.number);
    }
}

class ClickNumber{
    int number = 0;
    synchronized void clickBySynchronized(){
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    void clickByAtomicLong(){
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();
    void clickByLongAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum,0);
    void clickByLongAccumulator(){
        longAccumulator.accumulate(1);
    }
}
