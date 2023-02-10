package com.deng.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 13:45
 */
@Slf4j
public class PipelineServer {

    /**
     * 执行结果为 h1 h2 h3 h6 h5 h4
     * @param args
     */
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        // 添加处理器 head -> h1 -> h2 -> h3 -> h4 -> h5 -> h6
                        pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("1：{}",msg);
                                ByteBuf byteBuf = (ByteBuf)msg; // 将msg转化为ByteBuf
                                String str = byteBuf.toString(Charset.defaultCharset()); // 将ByteBuf转化为字符串
                                super.channelRead(ctx, str); // 将字符串传递给下一个ChannelInboundHandlerAdapter
                            }
                        });
                        pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("2：{}",msg);
                                Student student = new Student(msg.toString()); // 将1传递过来的字符串转化为Student对象
                                super.channelRead(ctx, student);
//                                ctx.fireChannelRead(student); // 与super.channelRead(ctx, student); 执行效果一样，都是把信息传递给下一个ChannelInboundHandlerAdapter
                            }
                        });

                        pipeline.addLast("h4",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("4：{}",msg);
                                super.write(ctx, msg, promise);
                            }
                        });

                        pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug("3：{}",msg);
                                super.channelRead(ctx, msg);
                                // pipeline.writeAndFlush 是从tail往前找 打印结果是 1 2 3 6 5 4
                                pipeline.writeAndFlush(ctx.alloc().buffer().writeBytes(msg.toString().getBytes()));
                                // ctx.writeAndFlush 是从当前的pipeline往前找 打印结果是 1 2 3 4
//                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(msg.toString().getBytes()));
                            }
                        });

                        pipeline.addLast("h5",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("5：{}",msg);
                                ByteBuf byteBuf = (ByteBuf)msg;
                                String string = byteBuf.toString(Charset.defaultCharset());
                                log.debug("5-1：{}",string);
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h6",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.debug("6：{}",msg);
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                .bind(8081);
    }

    static class Student{
        private String name;

        public Student(String name) {
            this.name = name;
        }
    }
}
