package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/16 18:46
 */
@Slf4j
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        Executors.newCachedThreadPool();                            // keypoint 最大线程数是Integer.MaxValue
        Executors.newSingleThreadExecutor();                        // keypoint 阻塞队列的大小是无界的--Integer.MaxValue
//        ExecutorService pool = Executors.newFixedThreadPool(2);   // keypoint 阻塞队列的大小是无界的--Integer.MaxValue
        // 自定义ThreadFactory
        ExecutorService pool = Executors.newFixedThreadPool(1, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"mypool_t"+t.getAndIncrement());
            }
        });


//
//        pool.execute(()->{
//            log.debug("1");
//        });
//        pool.execute(()->{
//            log.debug("2");
//        });
//        pool.execute(()->{
//            log.debug("3");
//        });


//        invokeAll(pool);
//        invokeAny(pool);
        shutdown(pool);
    }

    @Test
    public void testThreadPoolExecutor(){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,5,
                1000L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(3));
        for (int i = 0; i < 11; i++) {
            executor.execute(()->doSomeThing());
        }
        executor.shutdown();
    }

    private static void doSomeThing(){
        System.out.println(Thread.currentThread().getName()+"执行了");
        MyThreadUtil.sleep(100);
    }

    private static void invokeAll(ExecutorService pool) throws InterruptedException {
        List<Future<Object>> futures = pool.invokeAll(Arrays.asList(() -> {
            log.debug("1 begin");
            MyThreadUtil.sleep(1000);
            return "1";
        }, () -> {
            log.debug("2 begin");
            MyThreadUtil.sleep(500);
            return "2";
        }, () -> {
            log.debug("3 begin");
            MyThreadUtil.sleep(2000);
            return "3";
        }));

        futures.forEach(f-> {
            try {
                log.debug("{}",f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void invokeAny(ExecutorService pool) {
        try {
            String result = pool.invokeAny(Arrays.asList(() -> {
                log.debug("1 begin");
                MyThreadUtil.sleep(1000);
                return "1";
            }, () -> {
                log.debug("2 begin");
                MyThreadUtil.sleep(500);
                return "2";
            }, () -> {
                log.debug("3 begin");
                MyThreadUtil.sleep(2000);
                return "3";
            }));

            log.debug("{}",result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void shutdown(ExecutorService pool) {
        Future<String> result1 = pool.submit(() -> {
            log.debug("1 begin");
            MyThreadUtil.sleep(1000);
            log.debug("1 end");
            return "1";
        });
        Future<String> result2 = pool.submit(() -> {
            log.debug("2 begin");
            MyThreadUtil.sleep(500);
            log.debug("2 end");
            return "2";
        });
        Future<String> result3 = pool.submit(() -> {
            log.debug("3 begin");
            MyThreadUtil.sleep(2000);
            log.debug("3 end");
            return "3";
        });

        List<Runnable> runnables = pool.shutdownNow(); // 会把队列中的线程返回
//        try {
//            pool.shutdown();
//            pool.awaitTermination(1,TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.debug("other....size：{}",runnables.size());
    }
}
