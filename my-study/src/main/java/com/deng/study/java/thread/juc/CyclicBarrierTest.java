package com.deng.study.java.thread.juc;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc: 栅栏原理，也就是最后一个线程达到的时候，才会一起去执行后续的操作，否则都会等待
 * @Auther: dengyanliang
 * @Date: 2023/2/23 19:07
 */
@Slf4j
public class CyclicBarrierTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            log.debug("task1,task2 finish...");
        });

        // 执行一次
        invoke(pool, cyclicBarrier);

        // keypoint CyclicBarrier 创建一次，可以多次使用 注意和CountDownLatch的区别
        // keypoint 线程数要和CyclicBarrier数保持一致，如果不一致，则模拟不出来下面的重复使用场景
//        for (int i = 0; i < 3; i++) {
//            invoke(pool,cyclicBarrier);
//        }
        pool.shutdown();
    }

    private static void invoke(ExecutorService pool, CyclicBarrier cyclicBarrier) {
        pool.submit(()->{
            log.debug("task1 begin...");
            ThreadUtil.sleep(1000);
            try {
                cyclicBarrier.await(); // 也是减1
                log.debug("task1 end...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        pool.submit(()->{
            log.debug("task2 begin...");
            ThreadUtil.sleep(1000);
            try {
                cyclicBarrier.await();
                log.debug("task2 end...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        pool.submit(()->{
            log.debug("task3 begin...");
            ThreadUtil.sleep(3000);
            try {
                cyclicBarrier.await();
                log.debug("task3 end...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }
}
