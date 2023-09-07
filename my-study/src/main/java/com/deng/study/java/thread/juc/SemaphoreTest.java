package com.deng.study.java.thread.juc;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Desc: 信号量自测，信号量好比停场车，构造参数好比停车位数量
 * @Auther: dengyanliang
 * @Date: 2023/2/17 16:08
 */
@Slf4j
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();

                    log.debug("running...");
                    MyThreadUtil.sleep(1000);
                    log.debug("end....");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }).start();
        }
    }
}

