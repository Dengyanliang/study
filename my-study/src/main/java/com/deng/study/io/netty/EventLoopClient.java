package com.deng.study.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 14:49
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
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
                .connect(new InetSocketAddress("localhost",8082))
                .sync()// 同步方式
                .channel();
        log.debug("channel:{}",channel);
        System.out.println();
    }
}
