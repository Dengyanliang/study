package com.deng.study.io.nio;

import java.nio.ByteBuffer;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 18:48
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 32; i++) {
            buffer.put((byte)i);
        }

        // 进行翻转
        buffer.flip();

        ByteBuffer readnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readnlyBuffer.getClass()); // java.nio.HeapByteBufferR

        while(readnlyBuffer.hasRemaining()){
            System.out.println(readnlyBuffer.get());
        }
//        readnlyBuffer.put((byte)100); // 会报错 ReadOnlyBufferException
    }
}
