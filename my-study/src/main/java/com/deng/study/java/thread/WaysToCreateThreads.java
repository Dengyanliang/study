package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Desc: 思考有多少种方式，在main函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
 * @Auther: dengyanliang
 * @Date: 2023/9/19 12:55
 */
public class WaysToCreateThreads {

    private final static Object lockObj = new Object();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    1, 5,5,TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(5),new ThreadPoolExecutor.AbortPolicy());

    private static int sum(){
        return IntStream.range(0,10).sum();
    }

    // -------------------- 方法一 ------------------
    private static volatile Integer res1;
    private static void method1(){
        new Thread(() -> {
            res1 = sum();
        }, "t1").start();

        while (res1 == null){
            // 空循环，为了等待res1拿到结果
        }
        System.out.println("res1 = " + res1);
    }

    // -------------------- 方法二 ------------------
    private static void method2() throws Exception {
        FutureTask<Integer> task = new FutureTask<>(WaysToCreateThreads::sum);
        new Thread(task).start();
        Integer res2 = task.get();
        System.out.println("res2 = " + res2);
    }

    // -------------------- 方法三 ------------------
    private static volatile Integer res3;
    private static void method3() throws Exception {
        final CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            res3 = sum();
            cdl.countDown();
        }, "t1").start();

        cdl.await();
        System.out.println("res3 = " + res3);
    }

    // -------------------- 方法四 ------------------
    private static volatile Integer res4;
    private static void method4() throws Exception {
        Thread t1 = new Thread(()-> res4 = sum());
        t1.start();
        t1.join();
        System.out.println("res4 = " + res4);
    }

    // -------------------- 方法五 ------------------
    private static volatile Integer res5;
    private static void method5() throws Exception {
        new Thread(() -> {
            // sleep一下，确保main线程先拿到锁后阻塞，如果Thread线程先拿到锁后存在main线程无法被唤醒而永久阻塞的可能性
            MyThreadUtil.sleep(20);
            synchronized (lockObj){
                System.out.println("开始 计算sum 了");
                res5 = sum();
                System.out.println("开始 notifyAll 了");
                lockObj.notifyAll();
                System.out.println("notifyAll 完了");
            }
        }, "t1").start();
        synchronized (lockObj){
            System.out.println("开始wait 了");
            lockObj.wait();
            System.out.println("wait 完了");
        }
        System.out.println("res5 = " + res5);
    }

    // -------------------- 方法六 ------------------
    private static volatile Integer res6;
    private static void method6() throws Exception {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                MyThreadUtil.sleep(20);
                lock.lock();
                res6 = sum();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        try {
            lock.lock();
            condition.await();
        } finally {
            lock.unlock();
        }
        System.out.println("res6 = " + res6);
    }

    // -------------------- 方法七 ------------------
    private static void method7() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(WaysToCreateThreads::sum);
        Integer res7 = completableFuture.get();
        System.out.println("res7 = " + res7);
    }

    // -------------------- 方法八 ------------------
    private static void method8() throws ExecutionException, InterruptedException {
        Future<Integer> future = threadPoolExecutor.submit(WaysToCreateThreads::sum);
        Integer res8 = future.get();
        System.out.println("res8 = " + res8);
    }

    // -------------------- 方法九 ------------------
    private static volatile Integer res9;
    private static void method9() throws InterruptedException {
        threadPoolExecutor.execute(()->{
            MyThreadUtil.sleep(20);
            synchronized (lockObj){
                res9 = sum();
                lockObj.notifyAll();
            }
        });
        synchronized (lockObj){
            lockObj.wait();
            System.out.println("res9 = " + res9);
        }
    }
    // -------------------- 方法十 ------------------
    private static volatile Integer res10;
    private static void method10() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        threadPoolExecutor.execute(()->{
            try {
                MyThreadUtil.sleep(20);
                lock.lock();
                res10 = sum();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        });

        try {
            lock.lock();
            condition.await();
            System.out.println("res10 = " + res10);
        } finally {
            lock.unlock();
        }
    }
    // -------------------- 方法十一 ------------------
    private static void method11() {
        final ForkJoinPool forkJoinPool = new ForkJoinPool(1); // 并发度
        RecursiveTask<Integer> task = new RecursiveTask<Integer>(){
            @Override
            protected Integer compute() {
                return sum();
            }
        };
        Integer res11 = forkJoinPool.invoke(task);
        System.out.println("res11 = " + res11);
    }
    // -------------------- 方法十二 ------------------
    private static void method12() throws InterruptedException, ExecutionException {
        // ExecutorCompletionService 中内置了一个Future的completionQueue，在任务调用完成后，
        // 会将submit返回的future放入到completionQueue。用户可以通过future.get()获取任务执行结果
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPoolExecutor);
        Future<Integer> future = completionService.submit(WaysToCreateThreads::sum);
        Integer res12 = future.get();
        System.out.println("res12 = " + res12);
    }
    // -------------------- 方法十二 ------------------
    private static void method13() throws InterruptedException {
        // 阻塞队列 take时会一直阻塞，直到队列中有数据为止
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);
        new Thread(() -> {
            try {
                System.out.println("blockingQueue.put begin ....");
                blockingQueue.put(sum());
                System.out.println("blockingQueue.put end ....");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        System.out.println("blockingQueue.take begin ....");
        Integer res13 = blockingQueue.take();
        System.out.println("blockingQueue.take end ....");

        System.out.println("res13 = " + res13);
    }

    // -------------------- 方法十四 ------------------
    private static volatile Integer res14;
    private static void method14() throws InterruptedException{
        // 信号量
        Semaphore semaphore = new Semaphore(0);
        new Thread(() -> {
            res14 = sum();
            System.out.println("semaphore.release begin ....");
            semaphore.release();
            System.out.println("semaphore.release end ....");
        }, "t1").start();

        System.out.println("semaphore.acquire begin ....");
        semaphore.acquire();
        System.out.println("semaphore.acquire end ....");

        System.out.println("res14 = " + res14);
    }
    // -------------------- 方法十五 ------------------
    private static volatile Integer res15;
    private static void method15(){
        // Phaser Phaser又称“阶段器”，用来解决控制多个线程分阶段共同完成任务的情景问题。
        // 它与CountDownLatch和CyclicBarrier类似，都是等待一组线程完成工作后再执行下一步，协调线程的工作。
        // 但在CountDownLatch和CyclicBarrier中我们都不可以动态的配置parties，而Phaser可以动态注册需要协调的线程，
        // 相比CountDownLatch和CyclicBarrier就会变得更加灵活
        Phaser phaser = new Phaser(2);
        new Thread(() -> {
            res15 = sum();
            phaser.arriveAndAwaitAdvance(); // 达到并等待其他线程到达
        }, "t1").start();

        phaser.arriveAndAwaitAdvance();
        System.out.println("res15 = " + res15);
    }

    // -------------------- 方法十六 ------------------
    private static void method16() throws InterruptedException {
        // Exchange用于在两个线程之间进行数据交换，当两个线程都执行到了同步点之后（有一个没有执行到就一直等待，也可以设置等待超时时间），
        // 就将自身线程的数据与对方交换
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                exchanger.exchange(sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Integer res16 = exchanger.exchange(0);
        System.out.println("res16 = " + res16);
    }

    // -------------------- 方法十七 ------------------
    private static volatile Integer res17;
    private static void method17(){
        // 执行完await之后，会回调CyclicBarrier第二个参数的Runnable实现
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1,() ->
        {
            System.out.println("2222222");
            System.out.println("res17 = " + res17);
            System.out.println("3333333");
        });
        new Thread(() -> {
            res17 = sum();
            try {
                System.out.println("1111111");
                cyclicBarrier.await();
                System.out.println("4444444");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t1").start();
    }

    // -------------------- 方法十八 ------------------
    private static volatile Integer res18;
    private static void method18(){
        new Thread(() -> {
            res18 = sum();
        }, "t1").start();
        MyThreadUtil.sleep(200);
        System.out.println("res18 = " + res18);
    }

    // -------------------- 方法十九 ------------------
    private static volatile Integer res19;
    private static void method19() throws InterruptedException {
        Object lockObj = new Object();
        new Thread(() -> {
            res19 = sum();
        }, "t1").start();
        synchronized (lockObj){
            lockObj.wait(500);
            System.out.println("res19 = " + res19);
        }
    }

    // -------------------- 方法二十 ------------------
    private static volatile Integer res20;
    private static void method20() {
        new Thread(() -> {
            res20 = sum();
        }, "t1").start();
        // 阻塞当前线程，最长不超过nanos纳秒，返回条件在park()的基础上增加了超时返回
        LockSupport.parkNanos(100 * 1000 * 1000L);
        System.out.println("res20 = " + res20);
    }

    // -------------------- 方法二十一 ------------------
    private static volatile Integer res21;
    private static void method21() {
        new Thread(() -> {
            res21 = sum();
        }, "t1").start();
        // 阻塞当前线程，直到deadline时间
        LockSupport.parkUntil(System.currentTimeMillis() + 500);
        System.out.println("res21 = " + res21);
    }

    // -------------------- 方法二十二 ------------------
    private static volatile Integer res22;
    private static void method22() {
        Thread t1 = new Thread(() -> {
            res22 = sum();
        }, "t1");
        t1.start();
        while (t1.isAlive()){

        }
        System.out.println("res22 = " + res22);
    }
    // -------------------- 方法二十三 ------------------
    private static volatile Integer res23;
    private static void method23() {
        Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            // sleep一下，确保main线程先拿到锁后阻塞，如果Thread线程先拿到锁后存在main线程无法被唤醒而永久阻塞的可能性
//            ThreadUtil.sleep(100);
            res23 = sum();
            LockSupport.unpark(mainThread);
        }, "t1").start();

        LockSupport.park();
        System.out.println("res23 = " + res23);
    }

    // -------------------- 方法二十四 ------------------
    private static volatile Integer res24;
    private static void method24() throws InterruptedException, BrokenBarrierException {
        CyclicBarrier cb = new CyclicBarrier(2);
        new Thread(() -> {
            res24 = sum();
            try {
                System.out.println("method24 wait 1 begin ...");
                cb.await(); // 这里使用了CyclicBarrier的特性，只有所有线程都达到可执行的条件才会一起去执行下去，否则都等待
                System.out.println("method24 wait 1 end ...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        System.out.println("method24 wait 2 begin ...");
        cb.await();
        System.out.println("method24 wait 2 end ...");

        System.out.println("res24 = " + res24);
    }


    public static void main(String[] args) throws Exception {
        method1();
        method2();
        method3();
        method4();
        method5();
        method6();
        method7();
        method8();
        method9();
        method10();
        method11();
        method12();
        method13();
        method14();
        method15();
        method16();
        method17();
        method18();
        method19();
        method20();
        method21();
        method22();
        method23();
        method24();
    }

}
