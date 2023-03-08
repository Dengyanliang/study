package com.deng.study.java.io.nio;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.deng.study.util.ByteBufferUtil.debugAll;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/1 07:17
 */
@Slf4j
public class NIOServer {
    /**
     * 同步阻塞模式：这里使用循环方式，遍历客户端的连接，依旧是阻塞的，客户端太多的时候，会忙不过来
     * 这里和BIO一样
     * @throws IOException
     */
    @Test
    public void testSyncBlockMode() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        log.debug("-------server blockMode 启动等待中------------");
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8888));

        // 存放SocketChannel集合
        List<SocketChannel> socketChannelList = new ArrayList<>();
        while (true) {
            log.debug("连接中....");
            // 阻塞模式，意味着线程停止运行
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("------blockMode socketChannel--------"); // 在非阻塞模式下，accept()即使没有数据，也会执行该行代码
            if (socketChannel != null) {
                log.debug("成功连接...{}", socketChannel);
                socketChannelList.add(socketChannel);
                log.debug("socketChannelList：{}", socketChannelList.size());
            }

            for (SocketChannel channel : socketChannelList) {
                log.debug("before read...");
                // keypoint 阻塞方法，线程停止运行，也就是当前线程阻塞后，会阻塞另外的方法去执行任务
                int readLength = channel.read(buffer);
                if (readLength > 0) {
                    debugAll(buffer, readLength);
                }
            }
        }
    }

    /**
     * 同步非阻塞模式：这里使用循环方式。没有连接或者数据读取的时候，也一直在运行，所以这种场景在生产上没有什么用，只是用来测试
     * 这是NIO的典型写法，使用循环方式，这里对应linux/unix的select函数
     *
     * @throws IOException
     */
    @Test
    public void testSyncNotBlockMode() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        log.debug("-------server notBlockMode 启动等待中------------");

        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8888));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);

        /**
         * select 其实就是把NIO中用户态要遍历的fd数组（我们每一个socket连接存入到ArrayList里面的那个）拷贝到了内核态，
         * 让内核态来遍历，因为用户态判断socket是否有数据还是要调用内核态的，
         * 所以拷贝到内核态后，遍历判断的时候就不用一直在用户态和内核态频繁切换了
         */
        // 存放SocketChannel集合
        List<SocketChannel> socketChannelList = new ArrayList<>();
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept(); // keypoint 这里不会阻塞，会继续后续的运行
//            System.out.println("------blockMode socketChannel--------"); // 在非阻塞模式下，accept()即使没有数据，也会执行该行代码
            if (socketChannel != null) {
                log.debug("成功连接...{}", socketChannel);
                  socketChannel.configureBlocking(false); // keypoint 这里要对客户端也设置为非阻塞，否则会阻塞其他线程的运行
                socketChannelList.add(socketChannel);
                log.debug("socketChannelList：{}", socketChannelList.size());
            }
            for (SocketChannel channel : socketChannelList) {
                int readLength = channel.read(buffer);
                if (readLength > 0) {
                    debugAll(buffer, readLength);
                }
            }
        }
    }

    /**
     * 测试IO多路复用
     *
     * @throws IOException
     */
    @Test
    public void testIoMultiplexing() throws IOException {
        // 1、创建ServerSocketChannel--非阻塞模式
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8789));
        serverSocketChannel.configureBlocking(false);

        // 创建Selector，管理多个Channel
        Selector selector = Selector.open();

        // 建立selector和channel的联系（把selector注册到serverSocketChannel上），并且只关注accept事件
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 以下两句和上面那句含义相等
        SelectionKey sscKey = serverSocketChannel.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
//
        log.info("ServerSocketChannel注册的key:{}", sscKey);
        while (true) {
            // 没有事件发生，线程阻塞；有事件发生，才会继续后面的操作
            selector.select();
            log.info("-------------------");

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            log.info("所有的selectionKeys：{}", JSON.toJSON(selectionKeys));

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("当前遍历到的key：{}", JSON.toJSON(key));

                // 处理key时，要从selectedKeys集合中删除，否则下次处理就会有问题
                iterator.remove();

                if (key.isAcceptable()) { // 如果是accept，表示连接上了
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
//                    socketChannel.register(selector,SelectionKey.OP_READ); // 注册读事件

                    ByteBuffer buffer = ByteBuffer.allocate(16); // 作为一个附件注册到selectionKey上，和channel之间是一一对应的
                    SelectionKey scKey = socketChannel.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.info("SocketChannel注册的key：{}", scKey);
                    log.info("SocketChannel：{}", socketChannel);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel(); // 拿到触发事件的channel
                    ByteBuffer buffer = (ByteBuffer) key.attachment(); // 读取selectionKey上关联的附件

                    int readCount = channel.read(buffer);
                    System.out.println("读到的字节数：" + readCount);
                    if (readCount == -1) {
                        debugAll(buffer);
                        key.cancel();   // 处理完后，把当前SelectionKey删除，不然会循环调用
                    } else {
                        if (buffer.position() == buffer.capacity()) {  // 需要进行扩容
                            buffer.flip();  // 切换到读模式
                            // 进行扩容
                            ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                            newBuffer.put(buffer);
                            key.attach(newBuffer); // 将扩容后的buffer关联到附件上
                            System.out.println("newBuffer：" + newBuffer);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testSelectorWrite() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8889));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        SelectionKey sscKey = serverSocketChannel.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while (true) {
            // 没有事件发生，线程阻塞；有事件发生，才会继续后面的操作。这里是只阻塞5s
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("当前遍历到的key：{}，scKey.interestOps：{}", JSON.toJSON(key), key.interestOps());

                iterator.remove();

                if (key.isAcceptable()) { // 如果是accept，表示连接上了
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);

                    SelectionKey scKey = socketChannel.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.info("新注册的key：{}，scKey.interestOps：{}", JSON.toJSON(scKey), scKey.interestOps());

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 2000000; i++) {
                        sb.append("a");
                    }

                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    int writeCount1 = socketChannel.write(buffer);
                    System.out.println("writeCount1:" + writeCount1);

                    // 判断是否还有剩余内容
                    if (buffer.hasRemaining()) {
                        // 一次性写不完
//                        int writeCount = socketChannel.write(buffer);
//                        System.out.println(writeCount);
                        // 关注可写事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        // 把未写完的数据挂到scKey上
                        scKey.attach(buffer);

                        log.info("新注册的key增加的新功能：{}，scKey.interestOps：{}", JSON.toJSON(scKey), scKey.interestOps());
                    }
                }
                if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int writeCount2 = socketChannel.write(buffer);
                    System.out.println("writeCount2:" + writeCount2);

                    if (!buffer.hasRemaining()) {
                        key.attach(null); // 需要清除buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        log.info("处理完后的key：{}，scKey.interestOps：{}", JSON.toJSON(key), key.interestOps());
                    }
                }
            }
        }
    }

    /**
     * 测试一个主线程【boss】，多个子线程【worker】工作的场景
     *
     * @throws IOException
     */
    @Test
    public void testMultiThreadServer() throws IOException {
        Thread.currentThread().setName("boss");

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8858));
        serverSocketChannel.configureBlocking(false);

        Selector boss = Selector.open(); // 创建Selector
        serverSocketChannel.register(boss, SelectionKey.OP_ACCEPT, null);

        // 创建固定数量的worker并初始化--多个work工作
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }
        AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();

            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    log.debug("connected...{}", socketChannel.getRemoteAddress());

                    // 关联worker的selector
                    log.debug("before register...{}", socketChannel.getRemoteAddress());
                    workers[index.getAndIncrement() % workers.length].register(socketChannel);
                    log.debug("after register...{}", socketChannel.getRemoteAddress());
                }
            }
        }

    }

    class Worker implements Runnable {

        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false; // 是否初始化

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel socketChannel) throws IOException {
            if (!start) {
                log.debug("未register....start=false");
                thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
                log.debug("未register....start=true");
            }

            log.debug("before selector.wakeup()....");
            selector.wakeup(); // 唤醒selector.select() 方法
            log.debug("after selector.wakeup()....");

            log.debug("before socketChannel.register()....");
            socketChannel.register(selector, SelectionKey.OP_READ, null);
            log.debug("after socketChannel.register()....");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log.debug("before select....");
                    selector.select();
                    log.debug("after select....");

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        log.debug("current key：{}", JSON.toJSONString(key));
                        iterator.remove();

                        if (key.isReadable()) {
                            log.debug("当前key isReadable...");
                            ByteBuffer buffer = ByteBuffer.allocate(16);

                            SocketChannel channel = (SocketChannel) key.channel();
                            int read = channel.read(buffer);
                            log.debug("read count：{}，address：{}", read, channel.getRemoteAddress());

                            if (read == -1) {
                                key.cancel();
                            } else {
                                debugAll(buffer);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 第一种办法，使用队列【queue】 初始化线程和selector
       /*
       private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

       public void register(SocketChannel socketChannel) throws IOException {
            if(!start){
                thread = new Thread(this,name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            queue.add(()->{
                try {
                    log.debug("before socketChannel.register....");
                    socketChannel.register(selector,SelectionKey.OP_READ,null);
                    log.debug("after socketChannel.register....");
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });

            log.debug("before selector.wakeup()....");
            selector.wakeup(); // 唤醒selector.select() 方法
            log.debug("after selector.wakeup()....");
        }

        @Override
        public void run() {
            while (true) {
                try {
//                    Thread.sleep(30000);
                    log.debug("before select....");
                    selector.select();
                    log.debug("after select....");
                    Runnable task = queue.poll();
                    if(task != null){
                        log.debug("before task.run()....");
                        task.run(); // 会执行 socketChannel.register(selector,SelectionKey.OP_READ,null); 方法
                        log.debug("after task.run()....");
                    }

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        log.debug("current key：{}", JSON.toJSONString(key));
                        iterator.remove();

                        if(key.isReadable()){
                            log.debug("当前key isReadable...");
                            ByteBuffer buffer = ByteBuffer.allocate(16);

                            SocketChannel channel = (SocketChannel) key.channel();
                            int read = channel.read(buffer);
                            log.debug("read count：{}，address：{}", read, channel.getRemoteAddress());

                            if(read == -1){
                                key.cancel();
                            }else{
                                debugAll(buffer);
                            }
                        }
                    }

                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

}
