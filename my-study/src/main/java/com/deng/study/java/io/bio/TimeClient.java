package com.deng.study.java.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class TimeClient {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8888;
    public static void main(String[] args)  {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket(ADDRESS,PORT);

            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("hello 1");
            System.out.println("send succeed");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = in.readLine();
            System.out.println("resp:" + s);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(Objects.nonNull(out)){
                    out.close();
                }
                if(Objects.nonNull(in)){
                    in.close();
                }
                if(Objects.nonNull(socket)){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
