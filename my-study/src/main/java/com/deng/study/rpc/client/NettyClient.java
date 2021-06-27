package com.deng.study.rpc.client;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import com.deng.study.rpc.server.NettyRpcDecoder;
import com.deng.study.rpc.server.NettyRpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 21:47
 */
public class NettyClient {
    private final String host;
    private final int port;
    private Channel channel;
    private NettyClientHandler nettyClientHandler;

    /**
     * 连接服务端的端口号和地址
     * @param host
     * @param port
     */
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        connect();
    }

    private void connect() {
        try {
            EventLoopGroup group = new NioEventLoopGroup();

            // 客户端信息
            Bootstrap bs = new Bootstrap();
            bs.group(group).channel(NioSocketChannel.class).
                    handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("正在连接中。。。");

                            nettyClientHandler = new NettyClientHandler();
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyRpcEncoder(RequestModel.class));
                            pipeline.addLast(new NettyRpcDecoder(ResponseModel.class));
                            pipeline.addLast(nettyClientHandler); // 客户端处理类
                        }
                    });

            // 发起异步连接请求，绑定端口和host信息
            final ChannelFuture future = bs.connect(host,port).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("服务器连接成功");
                    }else{
                        System.out.println("服务器连接失败");
                        future.cause().printStackTrace();
                        group.shutdownGracefully();
                        connect();
                    }
                }
            });

            this.channel = future.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("e:" + e);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public NettyClientHandler getNettyClientHandler() {
        return nettyClientHandler;
    }
}
