package com.deng.study.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/9 16:44
 */
@Slf4j
public class HttpTest {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 4、boss负责处理连接 worker(child)负责处理读写。childHandler决定了worker(child)能执行哪些handler
            serverBootstrap.childHandler(
                    // 5、channel 代表和客户端进行数据读写的通道 Initalizer初始化，负责添加别的Handler
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            socketChannel.pipeline().addLast(new HttpServerCodec()); // 编码解码器
                            // 传入指定类型
                            socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {
                                    log.debug("url:{}",httpRequest.uri());

                                    // 创建响应对象
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(),
                                            HttpResponseStatus.OK);
                                    byte[] bytes = "hello,world".getBytes();

                                    // 写内容的长度
                                    response.headers().setInt(CONTENT_LENGTH,bytes.length);
                                    // 写响应内容
                                    response.content().writeBytes(bytes);

                                    // 写回响应
                                    channelHandlerContext.writeAndFlush(response);
                                }
                            });

//                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    // io.netty.handler.codec.http.DefaultHttpRequest 请求行+请求头
//                                    // io.netty.handler.codec.http.LastHttpContent$1  请求体
//                                    // 两个类型
//                                    log.debug("{}",msg.getClass());
//                                    if(msg instanceof HttpRequest){
//
//                                    }
//                                    if(msg instanceof HttpContent){
//
//                                    }
//                                }
//                            });
                        }
                    }
            );
            // 6、绑定监听端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
