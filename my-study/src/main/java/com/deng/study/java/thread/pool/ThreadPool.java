package com.deng.study.java.thread.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Desc: 线程池
 * @Auther: dengyanliang
 * @Date: 2023/2/16 15:49
 */
@Slf4j
public class ThreadPool {
    // 任务队列
    private BlockQueue<Runnable> taskQueue;

    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    // 超时时间
    private long timeout;

    // 超时单位
    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int queueCapcity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockQueue<>(queueCapcity);
        this.rejectPolicy = rejectPolicy;
    }


    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task){
        // 当任务数没有超过coreSize，直接交给work对象执行
        synchronized (workers) {
            if(workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.debug("新增 worker：{},task：{}",worker,task);
                workers.add(worker);
                worker.start();
            }else{
                // 当任务数超过coreSize，加入任务队列暂存
//                taskQueue.put(task);

                // 1、死等
                // 2、待超时时间等待
                // 3、让调用者放弃任务执行
                // 4、让调用者抛出异常
                // 5、让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }


    class Worker extends Thread{
        private Runnable task;
        public Worker(Runnable task) {
            this.task = task;
        }

        /**
         * 执行任务
         * 1、当task不为空时，执行任务
         * 2、当task执行完毕，从任务队列中获取任务继续执行
         */
        @Override
        public void run() {
            while(Objects.nonNull(task) || (task = taskQueue.poll(timeout,timeUnit)) != null){
                try {
                    log.debug("正在执行任务：{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("worker 被移除 {}", this);
                workers.remove(this);
            }
        }
    }

}
