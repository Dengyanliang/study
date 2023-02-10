package com.deng.study.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc: rpc响应处理器
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:38
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage message) throws Exception {
        log.debug("服务端响应消息：{}",message);
    }
}
