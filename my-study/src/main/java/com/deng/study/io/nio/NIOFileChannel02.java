package com.deng.study.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 11:50
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        File file = new File("test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((byte)file.length());

        // 将通道的数据读入到缓冲区
        fileChannel.read(byteBuffer);

        // 将byteBuffer的字节数据转成String
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
