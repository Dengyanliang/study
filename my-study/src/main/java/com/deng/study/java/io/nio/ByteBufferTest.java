package com.deng.study.java.io.nio;

import com.deng.common.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/5 17:17
 */
@Slf4j
public class ByteBufferTest {

    @Test
    public void testFileChannel() {
        // 有两种方式读取文件内容
        // 1、输入输出流 2、RandomAccessFile
        try (FileChannel channel = new FileInputStream("test.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // 从channel读取数据，向buffer写入数据
                int len = channel.read(buffer);
                log.info("读到的字节长度：{}", len);
                if (len == -1) {
                    break;
                }
//                ByteBuffer put = buffer.put(buffer);
//                if(put == null){
//                    break;
//                }

                // 切换buffer到读模式
                buffer.flip();

                // 是否还有剩余数据未读取
                while (buffer.hasRemaining()) {
                    byte b = buffer.get(); // 一次读一个字节
                    String s = new String(new byte[]{b}); // TODO 会有乱码
                    log.info("实际字节：{}", s);
                }
                buffer.clear(); // 调用clear切换至写模式，此时buffer会清空，通过channel往buffer中写
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testReadWrite() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{0x62, 0x63, 0x64});
        ByteBufferUtil.debugAll(buffer);
//
        buffer.flip();  // 切换到读模式，从头开始读取    flip和compact可以进行切换
        System.out.println(buffer.get());   // 读取一个
        buffer.compact();   // 把未读取完的部分向前移动，然后切换至写模式

        ByteBufferUtil.debugAll(buffer);
        buffer.put(new byte[]{0x65, 0x66});
        ByteBufferUtil.debugAll(buffer);

        ByteBuffer buffer2 = ByteBuffer.allocate(10);
        buffer2.put(new byte[]{'a', 'b', 'c', 'd'});

//        buffer.flip();
//        buffer.rewind();  // 从头开始读取
//        System.out.println(buffer.get());

//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.mark();      // 加标记，标记索引2的位置
//        System.out.println("mark()后。。。");
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.reset();     // 将position重置到索引2
//        System.out.println("reset()后。。。");
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());

        System.out.println(buffer2.get(2));  // 和get()的区别是，get(i)不会影响索引的位置
        ByteBufferUtil.debugAll(buffer2);
    }

    @Test
    public void testAllocateAndDirectAllocate() {
        System.out.println(ByteBuffer.allocate(10));          // HeapByteBuffer java堆内存，读写效率比较低，受到GC的影响
        System.out.println(ByteBuffer.allocateDirect(10));    // DirectByteBuffer 直接内存，读写效率高（少一次拷贝），不会受到GC影响，分配效率低
    }


    /**
     * 将字符串转化为byte
     */
    @Test
    public void testByteBufferString() {
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer1);

        // 第二种方式 Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer2);

        // wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(buffer3);

        String str = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str);
    }

    /**
     * 分散读
     */
    @Test
    public void testScatteringReads() {
        // 有两种方式读取文件内容
        // 1、输入输出流 2、RandomAccessFile
        try (FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(3);
            ByteBuffer buffer3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});

            buffer1.flip();
            buffer2.flip();
            buffer3.flip();

            ByteBufferUtil.debugAll(buffer1);
            ByteBufferUtil.debugAll(buffer2);
            ByteBufferUtil.debugAll(buffer3);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 集中写
     */
    @Test
    public void testScatteringWrites() {
        // 有两种方式读取文件内容
        // 1、输入输出流 2、RandomAccessFile
        try (FileChannel channel = new RandomAccessFile("words2.txt", "rw").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
            ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
            ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");
            channel.write(new ByteBuffer[]{buffer1, buffer2, buffer3});
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /**
     * 处理粘包半包
     */
    @Test
    public void testSplit() {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                System.out.println("source.position()：" + source.position() + "，i:" + i);
                int length = i + 1 - source.position(); // 计算length长度，因为i是从0开始，position从1开始，所以要加1
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
