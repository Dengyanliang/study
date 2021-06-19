package com.deng.study.rpc.client;

import com.deng.study.rpc.model.RequestModel;
import io.netty.channel.Channel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 22:33
 */
public class NettyClientMain {

    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1",8082);
        client.start();


        Channel channel = client.getChannel();
        RequestModel requestModel = new RequestModel();
        requestModel.setServiceName("123");
        requestModel.setMethodName("ss");
        channel.writeAndFlush(requestModel);
    }
}
