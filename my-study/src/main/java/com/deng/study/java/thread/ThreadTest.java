package com.deng.study.java.thread;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.concurrent.*;

@SpringBootTest
public class ThreadTest {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * org.junit.jupiter.api.Test可以加载spring容器中的配置、bean之类的信息
     * org.junit.Test 不可以加载，只是简单的测试
     */
    @Test
    public void test(){
        if(Objects.isNull(threadPoolExecutor)){
            threadPoolExecutor = new ThreadPoolExecutor(10,30,30,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(500));
        }

        String name = "zhangsan";
        CompletableFuture<String> future =  CompletableFuture.supplyAsync(() -> print(name), threadPoolExecutor);
        String result2 = null;
        try {
            result2 = future.get(); // 注意和join的区别，需要抛出异常，而join不需要抛出异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result2);

        String result = future.join();
        System.out.println(result);
    }

    private String print(String s){
        try {
            System.out.println("sleep begin");
            Thread.sleep(3000);
            System.out.println("sleep end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello:" + s;
    }
}
