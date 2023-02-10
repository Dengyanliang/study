package com.deng.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:13
 */
@Slf4j
public class RpcClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MyMessageCodecShareable messageCodec = new MyMessageCodecShareable();

        RpcResponseMessageHandler responseMessageHandler = new RpcResponseMessageHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ProcotolFrameDecoder());
                    socketChannel.pipeline().addLast(loggingHandler);
                    socketChannel.pipeline().addLast(messageCodec);
                    socketChannel.pipeline().addLast(responseMessageHandler);
                }
            });
            Channel channel = bootstrap.connect("localhost", 8088).sync().channel();

            RpcRequestMessage message = new RpcRequestMessage(
                    1,
                    "com.deng.study.netty.HelloService",
                    "sayHello",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"张三"}
            );
            channel.writeAndFlush(message);

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error",e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
