package com.deng.study.rpc.server;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 21:22
 */
public class NettyServer {

    public void bind(int port) throws Exception {
        // bossGroup是parentGroup，负责处理TCP/IP连接的
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // workerGroup是childGroup，负责处理channel（通道）的I/O事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128) // 初始化服务端可连接队列，指定了队列的大小128
                .childOption(ChannelOption.SO_KEEPALIVE,true) // 保持长连接
                .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时触发操作
                    @Override
                    protected void initChannel(SocketChannel channel)  {
                        channel.pipeline()
                                .addLast(new NettyRpcDecoder(RequestModel.class)) // 解码request
                                .addLast(new NettyRpcEncoder(ResponseModel.class)) // 编码response
                                .addLast(new NettyServerHandler()); // 使用ServerHandler来处理接收到的消息
                    }
                });

        // 绑定监听端口，调用sync同步阻塞方法等待绑定操作完
        ChannelFuture future = sb.bind(port).sync();

        if(future.isSuccess()){
            System.out.println("服务端启动成功");
        }else{
            System.out.println("服务端启动失败");
            future.cause().printStackTrace();
            // 关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        // 成功绑定到端口之后，给channel增加一个管道关闭的监听器，并同步阻塞，知道channel关闭，线程才会往下执行，结束进程
        future.channel().closeFuture().sync();
    }

}
