package com.deng.study.java.thread;

/**
 * @Desc: LockSupport 只能有一个许可证
 * @Auther: dengyanliang
 * @Date: 2023/2/22 14:55
 */
public class LockSupportTest {
    public static void main(String[] args) {
        Thread.currentThread().isInterrupted(); // 底层调用的isInterrupted，入参是false，也就是不会清除当前的中断标志位
        Thread.interrupted(); // 底层调用的是isInterrupted，入参是true Thread.currentThread().isInterrupted(true);

        Thread t = new Thread();
        t.interrupt();
    }
}
