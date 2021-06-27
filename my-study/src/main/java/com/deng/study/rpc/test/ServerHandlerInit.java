package com.deng.study.rpc.test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/26 23:11
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("responseEncode", new HttpResponseEncoder());
        pipeline.addLast("requestDecode", new HttpRequestDecoder());

        pipeline.addLast("objectAggregator", new HttpObjectAggregator(1024));
        pipeline.addLast("contentCompressor", new HttpContentCompressor());

        pipeline.addLast("httpServerHandler", new HttpServerHandler());
    }
}
