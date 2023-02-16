package com.deng.study.java.thread;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Desc: Unsafe类的实现
 * @Auther: dengyanliang
 * @Date: 2023/2/16 10:45
 */
@Slf4j
public class UnsafeAccessor {

    private static final Unsafe unsafe;

    static {
        try {
            unsafe = createUnsafe();
            log.debug("UnsafeAccessor unsafeClass：{}",unsafe);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }

    /**
     * 创建Unsafe类
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Unsafe createUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true); // 设置该值，可以访问私有变量
        return  (Unsafe)field.get(null); // 传递null是因为该类的构造方法是无参的
    }

    /**
     * 测试Unsafe
     * @param args
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 使用反射获取Unsafe类
        Unsafe unsafe = createUnsafe();
        System.out.println(unsafe);

        // 获取字段域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));

        Student student = new Student();

        // 执行CAS操作  keypoint 因为所有的cas操作都是使用的unsafe类去操作的
        unsafe.compareAndSwapInt(student,idOffset,0,1);
        unsafe.compareAndSwapObject(student,nameOffset,null,"zhangsan");

        // 验证，获取结果
        System.out.println(student);
    }
}
