package com.deng.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Desc: 粘包/半包 服务端
 * @Auther: dengyanliang
 * @Date: 2023/2/9 10:51
 */
public class NianOrBanbaoServer {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        new ServerBootstrap()
                // 2、BossEventLoop WorkerEventLoop   一个selector+一个thread就叫一个group
                .group(worker)
                // 3、选择 服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                // 设置系统的接收缓冲器（滑动窗口）
//                .option(ChannelOption.SO_RCVBUF,3)
                // 设置netty的接收缓冲区 --- TODO 这里测试的是半包问题
                .childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16))
                // 4、boss负责处理连接 worker(child)负责处理读写。childHandler决定了worker(child)能执行哪些handler
                .childHandler(
                        // 5、channel 代表和客户端进行数据读写的通道 Initalizer初始化，负责添加别的Handler
                        new ChannelInitializer<NioSocketChannel>() {

                            // 12.2、连接建立后，调用初始化方法
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                // 这里添加具体Handler

                                // 使用行换行符
                                nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));

                                // 定长解码器
//                                nioSocketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));

                                nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                                // 自定义的handler
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    // 15、读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("server channelRead....");
                                    }
                                });
                            }
                        }
                )
                // 6、绑定监听端口
                .bind(8899);
    }
}
