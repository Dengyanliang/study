package com.deng.study.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/6 10:16
 */
@Slf4j
public class ByteBufferUtil {

    public static void debugAll2(ByteBuffer buffer){
        buffer.flip();  // 切换至读模式
        buffer.mark();  // 标记当前的position位置
//        System.out.println("mark()后：" + buffer);
        System.out.print(buffer + " | ");
        StringBuilder builder = new StringBuilder();
        while(buffer.hasRemaining()){
//            System.out.println((char) buffer.get());
//            char aChar = (char) buffer.get();
            builder.append(new String(new byte[]{buffer.get()}));
        }
        System.out.println("buffer中的数据："+builder.toString());
//        System.out.println("reset()前：" + buffer);
        buffer.reset(); // 恢复到position位置
//        System.out.println("reset()后：" + buffer);
        buffer.compact();   // 把未读取完的部分向前移动，然后切换至写模式
//        System.out.println("compact()后：" + buffer);
    }

    public static void debugAll(ByteBuffer buffer){
//        buffer.compact();  // 切换至读模式
//        buffer.rewind();
        System.out.print(buffer + " | ");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < buffer.position(); i++) {
            builder.append(new String(new byte[]{buffer.get(i)}));  // 使用buffer.get(i)去获取，不会影响索引的位置
        }
        System.out.println("buffer中的数据："+builder.toString());
//        buffer.compact();   // 把未读取完的部分向前移动，然后切换至写模式
    }

}
