package com.deng.study.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

import java.io.IOException;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/7 20:41
 */
public class NettyServer {

//    @Test
//    public void testServer(){
    public static void main(String[] args) {
        // 1、启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                // 2、BossEventLoop WorkerEventLoop   一个selector+一个thread就叫一个group
                .group(new NioEventLoopGroup())
                // 3、选择 服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                // 4、boss负责处理连接 worker(child)负责处理读写。childHandler决定了worker(child)能执行哪些handler
                .childHandler(
                        // 5、channel 代表和客户端进行数据读写的通道 Initalizer初始化，负责添加别的Handler
                        new ChannelInitializer<NioSocketChannel>() {

                            // 12.2、连接建立后，调用初始化方法
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                System.out.println("12.2.1 before server add StringDecoder....");
                                // 这里添加具体Handler
                                // 将ByteBuf转化为字符串的解码器
                                nioSocketChannel.pipeline().addLast(new StringDecoder());

                                System.out.println("12.2.2 before server add ChannelInboundHandlerAdapter....");
                                // 自定义的handler
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    // 15、读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("server channelRead....");
                                        // 打印上一步转换好的字符串
                                        System.out.println(msg);
                                    }
                                });
                            }
                        }
                )
                // 6、绑定监听端口
                .bind(8899);
    }

    @Test
    public void test1() throws IOException {
        new ServerBootstrap()
                // 2、BossEventLoop WorkerEventLoop   一个selector+一个thread就叫一个group
                .group(new NioEventLoopGroup())
                // 3、选择 服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                // 4、boss负责处理连接 worker(child)负责处理读写。childHandler决定了worker(child)能执行哪些handler
                .childHandler(
                        // 5、channel 代表和客户端进行数据读写的通道 Initalizer初始化，负责添加别的Handler
                        new ChannelInitializer<NioSocketChannel>() {

                            // 12.2、连接建立后，调用初始化方法
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                System.out.println("12.2.1 before server add StringDecoder....");
                                // 这里添加具体Handler
                                // 将ByteBuf转化为字符串的解码器
                                nioSocketChannel.pipeline().addLast(new StringDecoder());

                                System.out.println("12.2.2 before server add ChannelInboundHandlerAdapter....");
                                // 自定义的handler
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    // 15、读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("server channelRead....");
                                        // 打印上一步转换好的字符串
                                        System.out.println(msg);
                                    }
                                });
                            }
                        }
                )
                // 6、绑定监听端口
                .bind(8899);
//        System.in.read();
    }
}
