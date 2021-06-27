package com.deng.study.rpc.client;

import com.deng.study.rpc.model.ResponseModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 22:30
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ResponseModel> {

    private ChannelHandlerContext ctx;
    private ResponseModel response;
    private ChannelPromise promise; // 该功能和countDownLatch效果一样，都是同步获取数据，相比而言，更简单些
    private CountDownLatch countDownLatch;

    /**
     * 处理服务端返回的数据
     * @param channelHandlerContext
     * @param responseModel
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseModel responseModel) throws Exception {
        System.out.println("接收到server响应数据:"+responseModel.toString());

        promise.setSuccess();
//        countDownLatch.countDown();

        this.response = responseModel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive......");
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public void sendMessage(Object request){
        promise = ctx.newPromise();
        ctx.writeAndFlush(request);
    }

    public ResponseModel getResponse() {
        try {
            promise.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
