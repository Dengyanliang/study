package com.deng.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.net.InetSocketAddress;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/7 20:58
 */
public class NettyClient {

//    @Test
//    public void testClient() throws InterruptedException {
    public static void main(String[] args) throws InterruptedException {
        // 7、启动类
        new Bootstrap()
                // 8、添加EventLoop
                .group(new NioEventLoopGroup())
                // 9、选择客户端channel实现
                .channel(NioSocketChannel.class)
                // 10、添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 12.1、在建立连接后被调用
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        System.out.println("12.1.1 before client add StringEncoder....");
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                        System.out.println("12.1.2 after client add StringEncoder....");

                    }
                })
                // 11、连接到服务器
                .connect(new InetSocketAddress("localhost",8899))
                // 13、同步/阻塞方法，直到连接建立才会继续执行下面的方法
                .sync()
                // 代表连接对象
                .channel()
                // 14、向服务器发送数据
                .writeAndFlush("hello world，上海");
    }
}
