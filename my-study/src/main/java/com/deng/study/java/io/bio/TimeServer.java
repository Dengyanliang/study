package com.deng.study.java.io.bio;



import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/18 22:44
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        // 创建一个线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("-------111 服务器启动了。。。");
        while (true) {
            System.out.println("-------222 等待接收。。");
            Socket socket = serverSocket.accept();
//            bio(socket);
            bioByMutiThread(threadPool, socket);
        }
    }

    /**
     * 阻塞IO，一次只能处理一个客户端的请求，当一个请求没有处理完之前，不能处理第二个请求，也就是串行处理
     * @param socket
     */
    private static void bio(Socket socket){
        handler(socket);
    }

    /**
     * 伪阻塞IO，多线程接收数据。可以为每个请求开辟一个线程处理
     * @param threadPool
     * @param socket
     */
    private static void bioByMutiThread(ExecutorService threadPool, Socket socket) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("------多线程 接收到数据。。");
                handler(socket);
            }
        });
    }


    public static void handler(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            System.out.println("-------333 等待读数据。。。");
            while (true) {
                int readLength = inputStream.read(bytes);
                System.out.println("-------444 读到数据");

                if (readLength != -1) {
                    System.out.println(new String(bytes, 0, readLength)); // 不会乱码
                    System.out.println("==========" + UUID.randomUUID().toString());
                    System.out.println();
                } else {
                    System.out.println("-------555 数据已经读完。。。");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
