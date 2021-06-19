package com.deng.study.rpc.client;

import com.deng.study.rpc.model.ResponseModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 22:30
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ResponseModel> {

    /**
     * 处理服务端返回的数据
     * @param channelHandlerContext
     * @param responseModel
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseModel responseModel) throws Exception {
        System.out.println("接收到server响应数据:"+responseModel.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
