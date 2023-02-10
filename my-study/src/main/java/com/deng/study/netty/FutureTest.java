package com.deng.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 13:51
 */
@Slf4j
public class FutureTest {


    @Test
    public void testJdkFuture() throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(2000);
                return 50;
            }
        });
        log.debug("等待结果。。。。");
        Integer result = future.get();
        log.debug("结果是：{}",result);
    }

    @Test
    public void testNettyFuture() throws ExecutionException, InterruptedException, IOException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        io.netty.util.concurrent.Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(2000);
                return 50;
            }
        });
        log.debug("等待结果1。。。。");

        // 同步的方式获取结果
//        Integer result = future.get();
//        log.debug("结果是：{}",result);

        // 异步的方式获取结果
        future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>(){
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {
                log.debug("接收结果：{}",future.getNow());
            }
        });
        log.debug("等待结果2。。。。");
        System.in.read();
    }

    @Test
    public void testChannelFuture() throws InterruptedException, IOException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接到服务器
                // 异步非阻塞，main发起了调用，真正执行connect是nio线程
                .connect(new InetSocketAddress("localhost", 8082));

        // 使用sync方法同步处理结果
//        channelFuture.sync();
//        Channel channel = channelFuture.channel();
//        log.debug("channel:{}",channel);
//        channel.writeAndFlush("hello,world");

        // 使用异步的方式处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("testChannelFuture ----------");
                Channel channel = channelFuture.channel();
                log.debug("channel:{}",channel);
                channel.writeAndFlush("hello,world");
            }
        });

        System.in.read();
    }

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new LoggingHandler());
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8081));
        Channel channel = channelFuture.sync().channel();

        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String str = scanner.nextLine();
                if(str.equals("q")){
                    channel.close();
                    break;
                }else{
                    channel.writeAndFlush(str);
                }
            }
        },"input").start();

        // 获取CloseFuture 对象，
        ChannelFuture closeFuture = channel.closeFuture();
        log.debug("waiting close....");

//        closeFuture.sync(); // 1）同步处理关闭
//        log.debug("处理关闭之后的操作。。。"); // [main] DEBUG com.deng.study.io.netty.FutureTest  main线程处理关闭

        // 2）异步处理关闭
        closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            log.debug("处理关闭之后的操作。。。"); // [nioEventLoopGroup-2-1] DEBUG com.deng.study.io.netty.FutureTest nio线程处理关闭
            group.shutdownGracefully();  // 该方法可以把EventLoopGroup里面的线程停下来
        });
    }

}
