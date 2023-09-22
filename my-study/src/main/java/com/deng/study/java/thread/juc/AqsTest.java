package com.deng.study.java.thread.juc;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/17 10:22
 */
@Slf4j
public class AqsTest {

    public static void main(String[] args) {
        MyLock lock = new MyLock();


        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking...");
                MyThreadUtil.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking...");
                MyThreadUtil.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t2").start();

    }
}

class MyLock implements Lock {

    private MySync sync = new MySync();

    // 同步器类  这里是独占锁
    private static class MySync extends AbstractQueuedSynchronizer{

        // 尝试加锁
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                // 加上了锁，并设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        // 尝试解锁
        @Override
        protected boolean tryRelease(int arg) {
            // 把当前线程移除
            setExclusiveOwnerThread(null);
            // 状态为复原
            setState(0);
            return true;
        }

        // 是否持有独占锁
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }

    }

    /**
     * 加锁，如果不成功会进入等待队列
     */
    @Override
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 加锁，可打断
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁，一次
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 尝试加锁，带超时时间
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        sync.release(1);
    }

    /**
     * 创建条件变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}