package com.deng.study.java.thread;


import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ThreadTest2 {

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

        ThreadUtil.sleep(500);
        log.debug("sleep end state：{}",t1.getState()); // TIMED_WAITING

        t1.interrupt();
        log.debug("interrupt end state：{}",t1.getState()); // TIMED_WAITING

        ThreadUtil.sleep(3000);

    }



}
