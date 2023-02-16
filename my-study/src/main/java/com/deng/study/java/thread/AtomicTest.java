package com.deng.study.java.thread;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.*;
import java.util.function.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/15 19:58
 */
@Slf4j
public class AtomicTest {


    @Test
    public void testAtomicInteger(){
        AtomicInteger i = new AtomicInteger(1);

        // 加法测试
//        System.out.println(i.getAndIncrement()); // 1, +1 --> i=2
//        System.out.println(i.incrementAndGet()); // i+1-->3,3
//
//        // 乘法
        i.updateAndGet(value->value*10);
//
//        // 除法
//        i.updateAndGet(value->value/5);

        // 模仿cas
        updateAndSet(i, p -> p * 10);
        System.out.println(i.get());

        updateAndSet(i, p -> p / 10);
        System.out.println(i.get());

        updateAndSet(i, p -> p - 10);
        System.out.println(i.get());

        updateAndSet(i, p -> p + 10);
        System.out.println(i.get());
    }

    private void updateAndSet(AtomicInteger i, IntUnaryOperator operator) {
        while(true){
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if(i.compareAndSet(prev,next)){
                break;
            }
        }
    }

    /**
     * 无法解决ABA问题
     */
    @Test
    public void testAtomicReference(){
        AtomicReference<String> reference = new AtomicReference<>("A");
        log.debug("main start....");

        String prev = reference.get();
        updateOther(reference);
        ThreadUtil.sleep(1000);
        log.debug("change A->C：{}",reference.compareAndSet(prev,"C"));
    }

    /**
     * 可以追踪数据被更改过多少次，通过第二个参数版本号来记录，可以解决ABA问题
     */
    @Test
    public void testAtomicStampedReference(){
        // 第二个参数是版本号
        AtomicStampedReference<String> reference = new AtomicStampedReference("A",0);
        log.debug("main start....");

        String prev = reference.getReference();
        int stamp = reference.getStamp();
        log.debug("prev:{},stamp:{}",prev,stamp);

        updateOther(reference);

        ThreadUtil.sleep(1000);
        log.debug("stamp:{}",stamp);
        log.debug("change A->C：{}",reference.compareAndSet(prev,"C",stamp,stamp+1));
        System.out.println(reference.getStamp());
    }

    /**
     * 这里不用追踪被更改的次数，只用记录是否更改过即可
     */
    @Test
    public void testAtomicMarkableReference() {
        // 第二个参数是版本号
        AtomicMarkableReference<String> reference = new AtomicMarkableReference("A", true);
        log.debug("main start....");

        String prev = reference.getReference();
        log.debug("prev:{}", prev);

        ThreadUtil.sleep(1000);
        log.debug("change A->C：{}", reference.compareAndSet(prev, "C", true, false));
        log.debug("第二次change A->C：{}", reference.compareAndSet(prev, "C", true, false));
    }


    private void updateOther(AtomicReference reference){
        new Thread(()->{
            log.debug("change A->B：{}",reference.compareAndSet(reference.get(),"B"));
        },"t1").start();

        ThreadUtil.sleep(1000);

        new Thread(()->{
            log.debug("change B->A：{}",reference.compareAndSet(reference.get(),"A"));
        },"t2").start();
    }

    private void updateOther(AtomicStampedReference reference){
        new Thread(()->{
            int stamp = reference.getStamp();
            log.debug("stamp:{}",stamp);
            log.debug("change A->B：{}",reference.compareAndSet(reference.getReference(),"B",stamp,stamp+1));
        },"t1").start();

        ThreadUtil.sleep(1000);

        new Thread(()->{
            int stamp = reference.getStamp();
            log.debug("stamp:{}",stamp);
            log.debug("change B->A：{}",reference.compareAndSet(reference.getReference(),"A",stamp,stamp+1));
        },"t2").start();
    }


    @Test
    public void testPrintArrayByFunction(){
        printArray(
                new Supplier<int[]>() {
                    @Override
                    public int[] get() {
                        return new int[10];
                    }
                },
                new Function<int[], Integer>() {
                    @Override
                    public Integer apply(int[] o) {
                        return o.length;
                    }
                },
                new BiConsumer<int[], Integer>() {
                    @Override
                    public void accept(int[] array, Integer index) {
                        array[index]++;
                    }
                },
                new Consumer<int[]>() {
                    @Override
                    public void accept(int[] array) {
                        System.out.println(Arrays.toString(array));
                    }
                }
            );

            printArray(
                    ()->new int[10],
                    (array)->array.length,
                    (array,index)->array[index]++,
                    (array)-> System.out.println(Arrays.toString(array))
            );


            printArray(
                    () -> new AtomicIntegerArray(10),
                    (array) -> array.length(),
                    (array, index) -> array.getAndIncrement(index),
                    array -> System.out.println(array)
            );

            printArray(
                    ()->new AtomicIntegerArray(10),
                    AtomicIntegerArray::length,
                    AtomicIntegerArray::getAndIncrement,
                    System.out::println
            );
    }

    /**
     * 测试数组数据
     * @param arraySupplier     数组，可以是线程安全，也可以是线程不安全的
     * @param lengthFunction    数组的长度
     * @param putConsumer       自增方法，回传array,index
     * @param printConsumer     打印数组的方法
     * @param <T>
     *
     *      Supplier 提供者 无中生有 用法：()->结果
     *      Function 函数   一个参数一个结果是Function (参数)->结果；两个参数一个结果是 BiFunction(参数1，参数2)->结果
     *      Consumer 消费者 一个参数没有结果是Consumer (参数)->void；两个参数没有结果是 BiConsumer(参数1，参>void
     *
     */
    private static <T> void printArray(Supplier<T> arraySupplier,
                                 Function<T,Integer> lengthFunction,
                                 BiConsumer<T,Integer> putConsumer,
                                 Consumer<T> printConsumer){
        List<Thread> threads = new ArrayList<>();
        T array = arraySupplier.get();
        Integer length = lengthFunction.apply(array);
        for (int i = 0; i < length; i++) {
            threads.add(new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array,j%length);
                }
            }));
        }

        threads.forEach(Thread::start); // 启动所有的线程
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });      // 等待线程结束

        printConsumer.accept(array);
    }

    private static <T> void adder(Supplier<T> supplier, Consumer<T> consumer){
        List<Thread> threads = new ArrayList<>();
        T adder = supplier.get();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(()->{
                for (int j = 0; j < 500000; j++) {
                    consumer.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();

        threads.forEach(Thread::start); // 启动所有的线程
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });      // 等待线程结束

        long end = System.nanoTime();

        System.out.println(adder + " cost：" + (end-start)/1000000);

    }


    @Test
    public void testAtomicReferenceFieldUpdater(){
        Student student = new Student();
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");
        updater.compareAndSet(student,null,"张三");
        System.out.println(student);
    }

    @Test
    public void testAdder(){
        adder(() -> new AtomicLong(0),
                (adder) -> adder.getAndIncrement());

        // keypoint LongAdder效率要高
        adder(() -> new LongAdder(),
                (adder) -> adder.increment());
    }




}
