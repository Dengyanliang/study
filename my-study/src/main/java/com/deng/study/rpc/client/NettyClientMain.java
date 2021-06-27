package com.deng.study.rpc.client;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import io.netty.channel.Channel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 22:33
 */
public class NettyClientMain {

    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1",8083);

        NettyClientHandler clientHandler = client.getNettyClientHandler();

//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        clientHandler.setCountDownLatch(countDownLatch);

        RequestModel requestModel = new RequestModel();
        requestModel.setServiceName("123");
        requestModel.setMethodName("ss");

        clientHandler.sendMessage(requestModel);

//        countDownLatch.await();

        ResponseModel response = clientHandler.getResponse();
        System.out.println("response:"+response);
    }
}
