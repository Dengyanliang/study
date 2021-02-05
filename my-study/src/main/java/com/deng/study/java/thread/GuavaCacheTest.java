package com.deng.study.java.thread;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/4 15:39
 */
public class GuavaCacheTest {

    private StopWatch stopWatch;

    public static void main(String[] args) throws InterruptedException {
        new GuavaCacheTest().test();
    }


    public void test() throws InterruptedException {
        stopWatch = new StopWatch();
        stopWatch.start();
        ExecutorService executor = new ThreadPoolExecutor(8, 32, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return queryData(key);
                    }
//
                    @Override
                    public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                        ListenableFutureTask<String> task = ListenableFutureTask.create(() -> load(key + 1));
                        executor.execute(task);
                        return task;
                    }
                });
        Thread thread7 = startLoadingCacheQuery("client7", cache);
        Thread thread8 = startLoadingCacheQuery("client8", cache);
        thread7.join();
        thread8.join();
        Thread.sleep(3000);
        Thread thread9 = startLoadingCacheQuery("client9", cache);
        Thread thread10 = startLoadingCacheQuery("client10", cache);
        thread9.join();
        thread10.join();
        Thread.sleep(5000);
    }

    private String queryData(String key) throws InterruptedException {
        log("queryData start");
        Thread.sleep(3000);
        System.out.println("------------------" + key.toString());
        log("queryData end");
        return key.toString();
    }

    private Thread startLoadingCacheQuery(String clientName, LoadingCache<String, String> cache) {
        Thread thread = new Thread(() -> {
            log("get start");
            try {
                cache.get(1+"");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            log("get end");
        });
        thread.setName(clientName);
        thread.start();
        return thread;
    }

    private void log(String msg) {
        System.out.println(String.format("%ds %s %s", System.currentTimeMillis()/ 1000, Thread.currentThread().getName(), msg));
    }
}
