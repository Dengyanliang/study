package com.deng.study.java.thread;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/16 21:06
 */
@Slf4j
public class TimerTest {

    public static void main(String[] args) {
//        testTimer();
        testScheduledExecutorService();
    }

    private static void testScheduledExecutorService(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        // 延时执行
//        pool.schedule(()->{
//            log.debug("1");
////            ThreadUtil.sleep(1000);
//            int i = 1/0;  // keypoint 即使出现了异常，还是会继续执行的
//        },1, TimeUnit.SECONDS);

//        // 延时执行
//        pool.schedule(()->{
//            log.debug("2");
//        },1, TimeUnit.SECONDS);
//
//        // 定时执行
//        pool.scheduleAtFixedRate(()->{
//            log.debug("3");
//            ThreadUtil.sleep(2000); // 会影响执行的频率，也就是会影响最后2个参数
//        },3,1,TimeUnit.SECONDS);

        // 定时延时执行，会把任务执行的时间+频率加上
        // 21:22:16.289 [pool-1-thread-3] DEBUG com.deng.study.java.thread.TimerTest - 4
        // 21:22:18.295 [pool-1-thread-3] DEBUG com.deng.study.java.thread.TimerTest - 4
//        pool.scheduleWithFixedDelay(()->{
//            log.debug("4");
//            ThreadUtil.sleep(1000);
//        },3,1,TimeUnit.SECONDS);



    }


    private static void testTimer() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask(){
            @Override
            public void run() {
                log.debug("1");
                ThreadUtil.sleep(1000);
            }
        };
        TimerTask task2 = new TimerTask(){
            @Override
            public void run() {
                log.debug("2");
            }
        };

        log.debug("main...");

        timer.schedule(task1,1000);
        timer.schedule(task2,1000);
    }
}
