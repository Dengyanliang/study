package com.deng.study.java.thread;

import java.util.concurrent.Callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Desc: 第三种实现多线程批量读取集合的方法
 * @Auther: dengyanliang
 * @Date: 2021/1/28 15:18
 */
public class ThreadDemoTest3 {


    public static void main(String[] args) throws Exception {

        // 开始时间
        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 25; i++) {
            list.add(i);
        }
        // 每500条数据开启一条线程
        int threadSize = 10;
        // 总数据条数
        int dataSize = list.size();
        // 线程数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;

        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 第一种方法，每次获取批量线程，提交任务
//        exec.submit(task);

        // 第二种方法，获取所有的批量线程，提交所有的任务

        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        Callable<Integer> task = null;
        List<Integer> cutList = null;

        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) { // 能够取整
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize); // 不能够取整
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }

            task = new ThreadDemo3(cutList);
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }

        List<Future<Integer>> results = exec.invokeAll(tasks);

        for (Future<Integer> future : results) {
            System.out.println(future.get());
        }

        // 关闭线程池
        exec.shutdown(); // 记得关闭
        System.out.println("线程任务执行结束");
        System.err.println("执行任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
    }
}

class ThreadDemo3 implements Callable{
    private List<Integer> dataList;

    public ThreadDemo3(List<Integer> dataList) {
        this.dataList = dataList;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "线程：" + dataList);
        return 1;
    }
}