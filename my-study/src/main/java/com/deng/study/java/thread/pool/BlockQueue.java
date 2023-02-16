package com.deng.study.java.thread.pool;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 阻塞队列
 * @Auther: dengyanliang
 * @Date: 2023/2/16 15:32
 */
@Slf4j
public class BlockQueue<T> {
    // 任务队列
    private Deque<T> queue = new ArrayDeque<>();

    // 锁
    private ReentrantLock lock = new ReentrantLock();

    // 生产者条件变量
    private Condition fullCondition = lock.newCondition();

    // 消费者条件变量
    private Condition emptyCondition = lock.newCondition();

    // 容量
    private int capcity;

    public BlockQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     *
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return
     */
    public T poll(long timeout, TimeUnit timeUnit){
        try {
            lock.lock();

            long nanos = timeUnit.toNanos(timeout); // 将时间转换为纳秒

            while(queue.isEmpty()){ // 队列为空时等待
                try {
                    nanos = emptyCondition.awaitNanos(nanos); // 该方法返回的是剩余时间
                    if(nanos <= 0){
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();// 队列不空时，获取头部元素，并从队里移除
            fullCondition.signalAll();  // 通知可以添加元素
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞获取
     * @return
     */
    public T take(){
        try {
            lock.lock();
            while(queue.isEmpty()){ // 队列为空时等待
                try {
                    log.debug("waiting.....");
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();// 队列不空时，获取头部元素，并从队里移除
            fullCondition.signal();  // 通知可以添加元素
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞添加
     * @param task
     */
    public void put(T task){
        try {
            lock.lock();
            while(queue.size() == capcity){
                try {
                    log.debug("等待加入任务队列 task：{}", task);
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 task：{}", task);
            queue.addLast(task);     // 放入队列末尾
            emptyCondition.signal(); // 通知可以获取元素
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task){
        try {
            lock.lock();
            if(queue.size() == capcity){
                log.debug("等待加入任务队列 task：{}", task);
                rejectPolicy.reject(this,task);
            }else{
                log.debug("加入任务队列 task：{}", task);
                queue.addLast(task);     // 放入队列末尾
                emptyCondition.signal(); // 通知可以获取元素
            }
        } finally {
            lock.unlock();
        }
    }


    public boolean offer(T task,long timeout, TimeUnit timeUnit){
        try {
            lock.lock();
            long nanos = timeUnit.toNanos(timeout);
            while(queue.size() == capcity){
                try {
                    log.debug("等待加入任务队列 task：{}", task);
                    if(nanos <= 0){
                        return false;
                    }
                    nanos = fullCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 task：{}", task);
            queue.addLast(task);     // 放入队列末尾
            emptyCondition.signal(); // 通知可以获取元素
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size(){
        try {
            lock.lock();
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

}
