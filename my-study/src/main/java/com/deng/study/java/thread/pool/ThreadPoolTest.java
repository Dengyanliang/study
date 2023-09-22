package com.deng.study.java.thread.pool;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/16 16:08
 */
@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) {

        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1, new RejectPolicy<Runnable>() {
            @Override
            public void reject(BlockQueue<Runnable> queue, Runnable task) {
                // 1、死等
                queue.put(task);
                // 2、待超时时间等待
                queue.offer(task,500,TimeUnit.MILLISECONDS);
//                queue.offer(task,3000,TimeUnit.MILLISECONDS);

                // 3、让调用者放弃任务执行
                log.debug("放弃执行。。。。{}",task);
                // 4、让调用者自己执行任务
                task.run();
                // 5、让调用者抛出异常
                throw new RuntimeException("任务执行失败。。。");
            }
        });

        for (int i = 0; i < 3; i++) {
            int id = i;
            threadPool.execute(()->{
                MyThreadUtil.sleep(2000);
                log.debug("正在执行：{}",id);
            });
        }

    }
}
