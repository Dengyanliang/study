package com.deng.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;


/**
 * @Desc: 粘包/半包 客户端
 * 解决办法：
 *      1、使用短连接，每次发送完之后，就断开连接
 *      2、使用定长方式
 *      3、使用行换行符
 *
 * @Auther: dengyanliang
 * @Date: 2023/2/9 10:51
 */
@Slf4j
public class NianOrBanbaoClient {

    public static void main(String[] args) throws InterruptedException {
        // 使用短连接处理
//        for (int i = 0; i < 10; i++) {
//            send();
//        }
        // 使用定长 或者 行换行符处理
        send2();
    }

    public static byte[] fillBytes(char c,int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if(i < len){
                sb.append(c);
            }else{
                sb.append("-");
            }
        }
        System.out.println(sb.toString());
        return sb.toString().getBytes();
    }

    public static String makeString(char c, int len){
        StringBuilder sb = new StringBuilder(len+2); // 加2的原因是后面要拼接 \n --->长度为2
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb.toString();
    }

    private static void send2() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 7、启动类
            Bootstrap bootstrap = new Bootstrap()
                    // 8、添加EventLoop
                    .group(worker)
                    // 9、选择客户端channel实现
                    .channel(NioSocketChannel.class)
                    // 10、添加处理器
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        // 12.1、在建立连接后被调用
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) {
                            nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                // 会在连接建立成功后，触发active时间
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("channelActive......");

                                    ByteBuf buf = ctx.alloc().buffer();
                                    char c = '0';
                                    Random r = new Random();
                                    // 使用换行符处理
                                    for (int i = 0; i < 10; i++) {
                                        String str = makeString(c,r.nextInt(256)+1);
                                        c++;
                                        buf.writeBytes(str.getBytes());
                                    }
                                    // 使用定长字符串处理
//                                    for (int i = 0; i < 10; i++) {
//                                        byte[] bytes = fillBytes(c, r.nextInt(10) + 1);
//                                        c++;
//                                        buf.writeBytes(bytes);
//                                    }
                                    ctx.writeAndFlush(buf);
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            log.debug("....1...");
            channelFuture.channel().closeFuture();
            log.debug("....2...");
        } catch (InterruptedException e) {
            log.error("client error...",e);
        } finally {
            log.debug("shutdown...");
            worker.shutdownGracefully();
        }
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 7、启动类
            Bootstrap bootstrap = new Bootstrap()
                    // 8、添加EventLoop
                    .group(worker)
                    // 9、选择客户端channel实现
                    .channel(NioSocketChannel.class)
                    // 10、添加处理器
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        // 12.1、在建立连接后被调用
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) {
                            nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                // 会在连接建立成功后，触发active时间
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("channelActive......");

                                    ByteBuf buf = ctx.alloc().buffer(16);
                                    buf.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
                                    ctx.writeAndFlush(buf);
                                    ctx.channel().close();
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            log.debug("....1...");
            channelFuture.channel().closeFuture();
            log.debug("....2...");
        } catch (InterruptedException e) {
            log.error("client error...",e);
        } finally {
            log.debug("shutdown...");
            worker.shutdownGracefully();
        }
    }

}
