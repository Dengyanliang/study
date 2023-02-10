package com.deng.study.java.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/1 07:34
 */
public class NIOClient {

    /**
     * 测试阻塞模式
     * @throws IOException
     */
    @Test
    public void testBlockMode() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);
        boolean connect = socketChannel.connect(socketAddress);
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

    /**
     * 测试非阻塞模式
     * @throws IOException
     */
    @Test
    public void testNotBlockMode() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.configureBlocking(false); // 设置为非阻塞

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8789);
//        System.out.println("waiting....");
        boolean connect = socketChannel.connect(inetSocketAddress);
        if(!connect){
            System.out.println("connectting.....");
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        String str = "bucuo o,world,test test test test";
//        String str = "bucuo o,world";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(buffer);
        System.out.println("发送了数据：" + str);
    }

    /**
     * 测试非阻塞模式
     * @throws IOException
     */
    @Test
    public void testNioRead() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress serverSocketAddress = new InetSocketAddress("127.0.0.1", 8889);
        socketChannel.connect(serverSocketAddress);
        int readCount = 0;
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            readCount += socketChannel.read(buffer);
            System.out.println(readCount);
            buffer.clear();
        }
    }

    @Test
    public void testMultiThread() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8858);
        boolean connect = socketChannel.connect(socketAddress);
        if(!connect){
            System.out.println("connectting.....");
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        String str = "try again...";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.out.println("发送了数据：" + str);
    }
}
