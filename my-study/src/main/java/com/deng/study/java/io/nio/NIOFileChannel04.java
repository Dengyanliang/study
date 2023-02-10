package com.deng.study.java.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 21:59
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("3.txt");

        FileChannel soureChannel = fileInputStream.getChannel();
        FileChannel descChannel = fileOutputStream.getChannel();

//        descChannel.transferFrom(soureChannel,0,soureChannel.size());

        soureChannel.transferTo(0,soureChannel.size(),descChannel);

        soureChannel.close();
        descChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
