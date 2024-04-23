package com.deng.study.netty;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 14:28
 */
@Slf4j
public class EventLoopTest {

    @Test
    public void test() throws IOException {
        // 创建事件循环组 NioEventLoopGroup 可以处理io事件、普通事件、定时任务
        // 这里指定的线程数是2，那么在执行的时候，就会轮询的去处理
        EventLoopGroup group = new NioEventLoopGroup(2);
        // DefaultEventLoopGroup 可以处理普通事件、定时任务
        EventLoopGroup group2 = new DefaultEventLoopGroup();

        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 执行普通任务
//        group.next().submit(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.debug("ok");
//        });

        // 执行定时任务
        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        },0,1, TimeUnit.SECONDS);


        log.debug("main");
        System.in.read();

    }
}
