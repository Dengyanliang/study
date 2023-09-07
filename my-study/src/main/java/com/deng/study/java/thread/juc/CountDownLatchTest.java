package com.deng.study.java.thread.juc;

import com.deng.common.util.MyThreadUtil;
import com.deng.common.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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
        testGame();
    }

    /**
     * 打印游戏玩家的加载进度
     * @throws InterruptedException
     */
    private static void testGame() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        String[] all = new String[10];
        for (int i = 0; i < 10; i++) {
            final int finalT = i;
            pool.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    all[finalT] = j + "%"; // 计算百分比
                    MyThreadUtil.sleep(RandomUtil.getInt(100)); // 加入睡眠时间模拟加载进度
                    System.out.print("\r"+Arrays.toString(all)); // keypoint 不换行，并且使用'\r'可以让后面的打印覆盖掉前面的值
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("\n游戏开始");
        pool.shutdown();
    }

    private static void testPool() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.submit(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(1000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });

        pool.submit(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(2000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });

        pool.submit(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(1500);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        });


        log.debug("wating...");
        countDownLatch.await();
        log.debug("wating end...");

        pool.shutdown();
    }

    private static void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(1000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(2000);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            MyThreadUtil.sleep(1500);
            countDownLatch.countDown();
            log.debug("end...{}",countDownLatch.getCount());
        }).start();


        log.debug("wating...");
        countDownLatch.await();
        log.debug("wating end...");
    }
}
