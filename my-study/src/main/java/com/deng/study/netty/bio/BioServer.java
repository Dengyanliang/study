package com.deng.study.netty.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/18 22:44
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动了。。。");
        while (true) {
            System.out.println("等待接收。。" + Thread.currentThread().getId() + "," + Thread.currentThread().getName());
            Socket socket = serverSocket.accept();
            System.out.println("接收到数据");

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            System.out.println("等待读数据。。。");
            int read = inputStream.read(bytes);
            System.out.println("读到数据");
            while (true) {
                if (read != -1) {
                    System.out.println(new String(bytes));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
