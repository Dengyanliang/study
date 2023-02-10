package com.deng.study.io.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 11:16
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello 中国，加油";
        FileOutputStream fileOutputStream = new FileOutputStream("1.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        byte[] bytes = str.getBytes();
        if(bytes.length > byteBuffer.capacity()){
            for (byte i = 0; i < bytes.length; i++) {
                byteBuffer.put(i);
            }
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }else{
            byteBuffer.put(str.getBytes());
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }
        fileOutputStream.close();
    }
}
