package com.deng.study.java.thread;

import com.deng.study.util.RandomUtil;
import com.deng.study.util.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc: ThreadLocal测试类
 * @Auther: dengyanliang
 * @Date: 2023/2/21 21:17
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        testThreadLocalRemove();
    }

    private static void testThreadLocalRemove(){
        House house = new House();
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                pool.submit(()->{
                    Integer beforeInt = house.threadLocalField.get();
                    try {
                        house.add();
                        Integer afterInt = house.threadLocalField.get();
                        System.out.println(Thread.currentThread().getName() + " " + "beforeInt：" + beforeInt +",afterInt：" + afterInt);
                    } finally {
                        house.threadLocalField.remove(); // keypoint 如果不remove，则属性值会一直累加
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static void saleHouseByThreadLocal() {
        House house = new House();
        AtomicInteger sum = new AtomicInteger(0);
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = RandomUtil.getInt(5);
                try {
                    for (int j = 1; j <= size; j++) {
    //                    house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    Integer saleCount = house.saleVolume.get();
                    sum.addAndGet(saleCount);
                    System.out.println(Thread.currentThread().getName() + " " + "号卖出："+saleCount);
                } finally {
                    house.saleVolume.remove(); // keypoint 为了程序的健壮性，这里在调用后删除ThreadLocal中的值
                }
            }, String.valueOf(i)).start();
        }
        ThreadUtil.sleep(500);
        System.out.println(Thread.currentThread().getName() + " " + "共计多少条：" + sum.get());
    }
}

class House {
    int saleCount = 0;

    /**
     * 使用synchronized加锁方式
     */
    public synchronized void saleHouse() {
        saleCount++;
    }

    /**
     * keypoint 使用ThreadLocal必须初始化initialValue方法，因为该方法默认是null
     * 这种方式不推荐使用
     */
//    ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>(){
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    // 推荐使用函数式编程
    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    /**
     * 使用ThreadLocal方式
     */
    public void saleVolumeByThreadLocal() {
        saleVolume.set(saleVolume.get() + 1);
    }

    ThreadLocal<Integer> threadLocalField  = ThreadLocal.withInitial(()->0);

    /**
     * 测试只set，不remove的情况
     */
    public void add(){
        threadLocalField.set(threadLocalField.get()+1);
    }
}

