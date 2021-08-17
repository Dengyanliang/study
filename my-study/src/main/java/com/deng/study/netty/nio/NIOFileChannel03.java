package com.deng.study.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 10:56
 */
public class NIOFileChannel03 {


    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.txt");
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(4);

        while(true){
            buffer.clear(); // 清空buffer
            int read = inputStreamChannel.read(buffer);
            if(read == -1){ // 表示读完
                break;
            }

            buffer.flip();
            outputStreamChannel.write(buffer);
        }
    }
}
