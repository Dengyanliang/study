package com.deng.common.util;

import io.netty.buffer.ByteBuf;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 17:43
 */
public class MyByteBufUtil {
    public static void log(ByteBuf buffer){

        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 ==0 ? 0:1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index：").append(buffer.readerIndex())
                .append(" write index：").append(buffer.writerIndex())
                .append(" capacity：").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf,buffer);
        System.out.println(buf.toString());
    }
}
