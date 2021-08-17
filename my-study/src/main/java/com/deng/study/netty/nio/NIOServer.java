package com.deng.study.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/1 07:17
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress(8789));

        serverSocketChannel.configureBlocking(false);

        // 创建Selector
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if(selector.select(1000) == 0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            SelectionKey selectionKey = null;
            while (keyIterator.hasNext()){
                selectionKey = keyIterator.next();
                keyIterator.remove();

                if(selectionKey.isValid()){
                    if(selectionKey.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        System.out.println("客户端连接成功，生成了一个 socketChannel:" + socketChannel);
                        socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }

                    if(selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();

                        ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();

                        channel.read(buffer);
                        System.out.println("从客户端读到了数据：" + new String(buffer.array()));
                    }
                }
            }
        }

    }
}
