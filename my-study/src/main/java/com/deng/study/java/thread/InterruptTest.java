package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @Desc: 中断测试
 * @Auther: dengyanliang
 * @Date: 2023/2/24 14:24
 */
@Slf4j
public class InterruptTest {
    private static volatile boolean isStop = false;
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    public static void main(String[] args) {
        testStaticInterrupted();
    }

    private static void testStaticInterrupted() {
        System.out.println(Thread.currentThread().getName() + " " + Thread.interrupted()); // false
        System.out.println(Thread.currentThread().getName() + " " + Thread.interrupted()); // false
        System.out.println("--1--");
        Thread.currentThread().interrupt(); // 中断标志位设置为true
        System.out.println("--2--");
        // Thread.interrupted() 做了两件事：
        // 1、返回当前线程的中断状态，测试当前线程是否已经被中断
        // 2、将当前线程的中断状态清零并重新设置为false
        System.out.println(Thread.currentThread().getName() + " " + Thread.interrupted()); // true
        System.out.println(Thread.currentThread().getName() + " " + Thread.interrupted()); // false
    }

    private static void test5() {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                System.out.println("----" + i + ",isInterrupted：" + Thread.currentThread().isInterrupted());
            }
            log.debug("t1线程调用interrupted后中断标识 02 ..{}", Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        boolean interrupted = t1.isInterrupted();
        log.debug("t1默认中断标识..{}", interrupted);

        MyThreadUtil.sleep(1);
        log.debug("interrupt...");
        t1.interrupt();
        interrupted = t1.isInterrupted();
        log.debug("t1线程调用interrupted后中断标识 01..{}", interrupted);

        MyThreadUtil.sleep(2000);
        interrupted = t1.isInterrupted(); // keypoint 2秒后，t1已经执行完了，再去中断，没有任何意义了。也就是中断不活动的线程不会产生任何影响
        log.debug("t1线程调用interrupted后中断标识 03..{}", interrupted);
    }

    private static void stopByAtomicBoolean() {
        new Thread(() -> {
            while (true){
                if(atomicBoolean.get()){
                    log.debug("isStop=true");
                    break;
                }
                log.debug("t1 ---hello atomicBoolean---");
            }
        }, "t1").start();

        MyThreadUtil.sleep(20);
        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void stopByVolatile() {
        new Thread(() -> {
            while (true){
                if(isStop){
                    log.debug("isStop=true");
                    break;
                }
                log.debug("t1 ---hello volatile---");
            }
        }, "t1").start();

        MyThreadUtil.sleep(20);
        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }


    private static void test3() {
        Thread t1 = new Thread(() -> {
            log.debug("park..");
            LockSupport.park();
            log.debug("unpark...");
//           log.debug("打断标记：{}",Thread.currentThread().isInterrupted());   // 打断标记为真时，会让park失效。。
            log.debug("打断标记：{}", Thread.interrupted());   // Thread.interrupted()在打断标记为真，会清除打断标记，那么后面park依然可以使用
            log.debug("再次获取打断标记：{}", Thread.currentThread().isInterrupted());   // 打断标记为真时，会让park失效。。

            log.debug("again park..");
            LockSupport.park();
            log.debug("again unpark...");
        }, "t1");
        t1.start();

        MyThreadUtil.sleep(1000);
        t1.interrupt(); // 打断后，可以执行park后面的内容
    }

    /**
     * 测试两阶段终止--依赖于Interrupt中断线程
     */
    private static void stopByInterrupt() {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        MyThreadUtil.sleep(3500);
        twoPhaseTermination.stop();
    }

    private static void test2() {
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("t1 is interrupted...");
                    break;
                }
            }
        }, "t1");
        t1.start();

        MyThreadUtil.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            MyThreadUtil.sleep(5000);
        }, "t1");
        t1.start();

        MyThreadUtil.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
        boolean interrupted = t1.isInterrupted();
        log.debug("interrupted..{}", interrupted);
    }
}

/**
 * 两阶段终止模式
 */
@Slf4j
class TwoPhaseTermination {
    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("当前线程被打断了...");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace(); // 为什么在异常处再次调用interrupt？
                    current.interrupt(); // keypoint 重新设置中断标记，因为此时中断标志已经被清除了，设置为false了。如果不设置，后面if判断时，就不会中断程序
                }
                log.debug("执行监控记录...");
            }
        }, "t1");
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}
