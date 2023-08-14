package com.deng.study.shardingsphere.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@Component
public class ThreadPool {

    @Bean("threadPoolExecutor")
    @Primary
    public ThreadPoolExecutor threadPoolExecutor(){
        log.info("ThreadPool init...");
        return new ThreadPoolExecutor(10,30,30,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000));
    }
}
