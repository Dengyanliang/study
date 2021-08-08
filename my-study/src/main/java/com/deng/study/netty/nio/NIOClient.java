package com.deng.study.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/1 07:34
 */
public class NIOClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8789);
        boolean connect = socketChannel.connect(inetSocketAddress);
        if(!connect){
            System.out.println("connectting.....");
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        String str = "hello,world";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(buffer);
    }
}
