package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/14 15:56
 */
@Slf4j
public class DeadLockTest {

    public static void main(String[] args) {
//        testDeadLock1();

        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("张三", c1, c2).start();
        new Philosopher("李四", c2, c3).start();
        new Philosopher("王五", c3, c4).start();
        new Philosopher("赵六", c4, c5).start();
        new Philosopher("田七", c5, c1).start();
    }

    private static void testDeadLock1() {
        Object A = new Object();
        Object B = new Object();
        Thread t1 = new Thread(()->{
            synchronized (A){
                try {
                    log.debug("lock A");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    log.debug("lock B");
                }
            }
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (B){
                try {
                    log.debug("lock B");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    log.debug("lock A");
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}


/**
 * 哲学家
 */
@Slf4j(topic = "Philosopher")
class Philosopher extends Thread{
    private Chopstick left; // 左手边的筷子
    private Chopstick right; // 右手边的筷子

    public Philosopher(String name,Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            // 用Synchronized会产生死锁
//            synchronized (left){
//                synchronized (right){
//                    eat();
//                }
//            }

            //

            if (left.tryLock()) {
                try {
                    if(right.tryLock()){
                        try{
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }

        }

    }

    /**
     * 吃饭
     */
    private void eat() {
        try {
            log.debug("eating");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 筷子类
 */
class Chopstick extends ReentrantLock {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }
}
