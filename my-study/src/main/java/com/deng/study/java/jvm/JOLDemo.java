package com.deng.study.java.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/20 16:47
 */
public class JOLDemo {
    public static void main(String[] args) {
        Object o = new Object();
//        System.out.println(VM.current().details());
//        System.out.println(VM.current().objectAlignment());

        // 前两行(object header)是Mark Word 8字节
        // keypoint 第三行(object header) 是类型指针4字节(本来应该是8字节)，原因是因为JVM默认开启了压缩 -XX:+UseCompressedClassPointers
        // 可以通过idea终端 执行 java -XX:+PrintCommandLineFlags -version看到
        // 可以通过在idea中配置 -XX:-UseCompressedClassPointers，把压缩关闭，再执行下，就可以对比出不一致的地方了
//        OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
//        0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
//        4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//        8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

//        Customer customer = new Customer();
//        System.out.println(ClassLayout.parseInstance(customer).toPrintable());
    }
}

class Customer{
    private int id;
    private boolean flag;
}
