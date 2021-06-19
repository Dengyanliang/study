package com.deng.study.rpc.server;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 21:38
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 接收client发送的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RequestModel requestModel = (RequestModel)msg;
        System.out.println("接收到客户端信息："+requestModel);

        // 返回数据
        String result = "success";

        ResponseModel responseModel = new ResponseModel();
        responseModel.setResult(result);
        ctx.write(responseModel);
    }


    /**
     * 通知处理器最后的channelRead()是当前批处理中最后一条消息时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕...");
        ctx.flush();
    }


    /**
     * 读操作时捕获到异常时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 客户端和服务端连接成功时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }
}
