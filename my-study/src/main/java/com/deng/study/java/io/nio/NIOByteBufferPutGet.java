package com.deng.study.java.io.nio;

import java.nio.ByteBuffer;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/7/25 18:51
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(1);
        buffer.putLong(20);
        buffer.putChar('商');
        buffer.putShort((short) 8);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
