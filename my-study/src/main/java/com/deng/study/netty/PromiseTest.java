package com.deng.study.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 14:14
 */
@Slf4j
public class PromiseTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 准备Event对象
        EventLoop eventLoop = new NioEventLoopGroup().next();

        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(()->{
            log.debug("开始计算...");
            try {
                int i = 1/0;
                Thread.sleep(2000);
            } catch (Exception e) {
//                e.printStackTrace();
                log.error("发生了异常。。。");
                promise.setFailure(e);  // 发生异常在这里设置，在promise.get()的时候会获取的异常结果
            }
            // 设置结果
//            promise.setSuccess(50);
        }).start();

        log.debug("等待结果...");
        log.debug("结果是：{}", promise.get());
    }
}
