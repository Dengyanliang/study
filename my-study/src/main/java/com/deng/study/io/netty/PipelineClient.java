package com.deng.study.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 16:27
 */
@Slf4j
public class PipelineClient {
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

        // 2）异步处理关闭
        closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            log.debug("处理关闭之后的操作。。。"); // [nioEventLoopGroup-2-1] DEBUG com.deng.study.io.netty.FutureTest nio线程处理关闭
            group.shutdownGracefully();  // 该方法可以把EventLoopGroup里面的线程停下来
        });
    }
}
