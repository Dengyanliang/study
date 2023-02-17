package com.deng.study.java.thread.juc;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc: 倒计时，等待所有线程执行完毕
 * @Auther: dengyanliang
 * @Date: 2023/2/17 16:27
 */
@Slf4j
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        testPool();
    }

    private static void testPool() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.submit(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(1000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });

        pool.submit(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(2000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });

        pool.submit(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(1500);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });


        log.debug("wating...");
        countDownLatch.await();
        log.debug("wating end...");
    }

    private static void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(1000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(2000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            ThreadUtil.sleep(1500);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();


        log.debug("wating...");
        countDownLatch.await();
        log.debug("wating end...");
    }
}
