package com.deng.study.java.thread;


import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadTest2 {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        new Thread(() -> {
            while (true){
                System.out.println("---每次添加一个byte[1]字节数组---");
                MyThreadUtil.sleep(100);
                list.add(new byte[10]);
            }
        }, "t1").start();

    }

    @Test
    public void testSleep(){
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.debug("wake up ....");
                e.printStackTrace();
            }
            log.debug("current state：{}",Thread.currentThread().getState()); // sleep之后，状态为RUNNABLE
            int count = 0;
            while (true){
                if(count == 10){
                    break;
                }
                log.debug("running...");
                count++;
            }
        }, "t1");

        log.debug("start before state：{}",t1.getState()); // NEW
        t1.start();
        log.debug("start end state：{}",t1.getState()); // RUNNABLE

        MyThreadUtil.sleep(500);
        log.debug("sleep end state：{}",t1.getState()); // TIMED_WAITING

        t1.interrupt();
        log.debug("interrupt end state：{}",t1.getState()); // TIMED_WAITING

        MyThreadUtil.sleep(3000);

    }



}
