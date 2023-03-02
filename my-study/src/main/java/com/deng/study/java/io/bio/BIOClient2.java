package com.deng.study.java.io.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BIOClient2 {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        Socket socket  = new Socket(ADDRESS,PORT);
        OutputStream outputStream = socket.getOutputStream();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
            if(str.equals("quit")){
                break;
            }
            outputStream.write(str.getBytes());
            System.out.println("----TimeClient2 input finish-------");
        }
        outputStream.close();
        socket.close();


    }

}
