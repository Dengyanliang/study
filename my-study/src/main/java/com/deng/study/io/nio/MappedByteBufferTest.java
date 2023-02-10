package com.deng.study.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 20:13
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)'G');
//        mappedByteBuffer.put(5,(byte)'1'); 此时会越界 所以最大不能超过size

        randomAccessFile.close();
    }
}
