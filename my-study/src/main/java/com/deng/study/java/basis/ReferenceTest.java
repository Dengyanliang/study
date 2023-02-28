package com.deng.study.java.basis;

import com.deng.study.util.ThreadUtil;
import org.junit.Test;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: 四种引用测试
 * @Auther: dengyanliang
 * @Date: 2023/2/22 11:16
 */
public class ReferenceTest {

    @Test
    public void testStrongReference(){
        MyObject myObject = new MyObject();
        System.out.println("gc before：" + myObject);
        myObject = null;
        System.gc(); // 人工开启GC，一般不用

        ThreadUtil.sleep(500);
        System.out.println("gc after：" + myObject);
    }

    @Test
    public void testSoftReference(){
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.gc();
        ThreadUtil.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " " + "gc after 内存够用：" + softReference.get());
        try {
            // 调试时需要再idea中配置参数 -Xms10m -Xmx10m 设置最大最小堆的内存都为10MB
            // 如果内存够用，则不会触发垃圾回收
            byte[] bytes = new byte[20 * 1024 * 1024]; // 20MB对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " " + "gc after 内存不够：" + softReference.get());
        }
    }

    /**
     * 内存够用时，触发了gc，也会回收对象
     */
    @Test
    public void testWeakReference(){
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println(Thread.currentThread().getName() + " " + "gc before 内存够用：" + weakReference.get());

        System.gc();

        ThreadUtil.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " " + "gc after 内存够用：" + weakReference.get());
    }

    /**
     * 虚引用难以模拟
     */
    @Test
    public void testPhantomReference(){
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject,referenceQueue);
        List<byte[]> list = new ArrayList<>();
        new Thread(() -> {
            while (true){
                list.add(new byte[1* 1024 * 1024]);
                ThreadUtil.sleep(200);
                System.out.println(phantomReference.get() + " " + "list add ok");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true){
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if(reference != null){
                    System.out.println("---有虚对象被回收加入了队列");
                    break;
                }
            }
        }, "t2").start();

        ThreadUtil.sleep(50000);

    }
}

class MyObject{
    /**
     * finalize的通常目的是在对象被不可撤销的丢弃之前执行清理操作
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("-----invoke finalize method----");
    }
}
