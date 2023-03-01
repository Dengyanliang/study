package com.deng.study.java.thread;


import com.deng.study.MyApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= MyApplication.class)
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
        long start = System.currentTimeMillis();
        String name = "zhangsan";
        CompletableFuture<String> future =  CompletableFuture.supplyAsync(() -> print(name), threadPoolExecutor);
        System.out.println("---------------------");
        CompletableFuture<String> future2 =  CompletableFuture.supplyAsync(() -> print2(name), threadPoolExecutor);

        String result = future.join(); // 阻塞式的，所以要放在最后，而不能放在29行后面，这样效率最高
        System.out.println("result:"+result);

        String result2 = future2.join();
        System.out.println("result2:" +result2); // 阻塞式的，所以要放在最后
        long end = System.currentTimeMillis();
        System.out.println("end-start: " + (end-start)); // 3004

        try {
            result2 = future.get(); // 注意和join的区别，需要抛出异常，而join不需要抛出异常
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("future.get(): "+result2);


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

    private String print2(String s){
        try {
            System.out.println("sleep begin 2");
            Thread.sleep(2000);
            System.out.println("sleep end 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2:" + s;
    }


    @Test
    public void testSleep(){
        Thread t1 = new Thread(() -> {
           log.debug("running...");
        }, "t1");
        log.debug("before state:{}",t1.getState());
        t1.start();
        log.debug("end state:{}",t1.getState());
    }


}
