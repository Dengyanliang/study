package com.deng.study.designpattern.creational.singleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/19 09:21
 */
public class SingletonTest {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,30,
            5, TimeUnit.SECONDS,new ArrayBlockingQueue<>(500));

    public static void main(String[] args) throws Exception {
//        concurrentTest(Singleton1.class);
//        singletonTest(Singleton1.getInstance());
//
//        concurrentTest(Singleton2.class);
//        singletonTest(Singleton2.getInstance());
//
//        concurrentTest(Singleton3.class);
//        singletonTest(Singleton3.getInstance());
//
//        concurrentTest(Singleton4.class);
//        singletonTest(Singleton4.getInstance());
//
//        concurrentTest(Singleton5.class);
//        singletonTest(Singleton5.getInstance());
//
//        concurrentTest(Singleton7.class);
//        singletonTest(Singleton7.getInstance());
//
//        concurrentTest(Singleton8.class);
//        singletonTest(Singleton8.getInstance());
//
//        threadPoolExecutor.shutdown();

//        concurrentTest(Singleton6.class);
        enumSingletonTest();
    }

    private static <T extends Singleton> void concurrentTest(Class<?> clazz) throws Exception{
        System.out.println("------------------" + clazz.getName() + "-------------------");
        CountDownLatch countDownLatch = new CountDownLatch(20);
        Object[] instanceArr = new Object[20];
        IntStream.range(0,20).forEach( i-> threadPoolExecutor.execute(()->{
            try {
                Method getInstanceMethod = clazz.getDeclaredMethod("getInstance");
                instanceArr[i] = getInstanceMethod.invoke(clazz);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }));

        countDownLatch.await();

        boolean isConcurrentSafety = true;
        Object ins = instanceArr[0];
        for (int i = 0; i < instanceArr.length; i++) {
            if(ins != instanceArr[i]){
                System.out.println(clazz.getName() + " 多线程不安全");
                isConcurrentSafety = false;
                break;
            }
        }
        if(isConcurrentSafety){
            System.out.println(clazz.getName() + " 多线程安全");
        }
    }


    /**
     * 测试单例是否存在克隆破坏、反射破坏、反序列化破坏
     * @param instance
     * @param <T>
     * @throws Exception
     */
    private static <T extends Singleton> void singletonTest(T instance) throws Exception{
        // 测试克隆破坏
        T cloneInstance = (T)instance.clone();
        if(instance == cloneInstance){
            System.out.println(instance.getClass().getName() + " 的单例没有被克隆破坏");
        }else{
            System.out.println(instance.getClass().getName() + " 的单例已被克隆破坏");
        }

        // 测试反序列化破坏
        File objFile = new File(SingletonTest.class.getResource("").getPath(),"obj.data");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objFile))){
            oos.writeObject(instance);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFile))){
            T deserializationInstance = (T)ois.readObject();
            if(instance == deserializationInstance){
                System.out.println(instance.getClass().getName() + " 的单例没有被反序列化破坏");
            }else{
                System.out.println(instance.getClass().getName() + " 的单例已被反序列化破坏");
            }
        }
        objFile.delete();


        // 测试反射破坏
        Constructor<?> constructor = Class.forName(instance.getClass().getName()).getDeclaredConstructor();
        constructor.setAccessible(true);

        T newInstance = (T)constructor.newInstance();
        if(instance == newInstance){
            System.out.println(instance.getClass().getName() + " 的单例没有被反射破坏");
        }else{
            System.out.println(instance.getClass().getName() + " 的单例已被反射破坏");
        }
    }

    private static void enumSingletonTest() throws Exception{
        System.out.println("-----------------------" + Singleton6.class.getName() +"-----------------------");
        // 测试克隆破坏
//        Singleton6.instance.clone();
        System.out.println("枚举的clone方法是protected访问权限，同时也是final方法，无法被重写，所以在外部无法调用枚举的clone");

        // 测试反序列化破坏
        File objFile = new File(SingletonTest.class.getResource("").getPath(),"obj.data");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objFile))){
            oos.writeObject(Singleton6.instance);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFile))){
            Singleton6 deserializationInstance = (Singleton6)ois.readObject();
            if(Singleton6.instance == deserializationInstance){
                System.out.println(Singleton6.class.getName() + " 的单例没有被反序列化破坏");
            }else{
                System.out.println(Singleton6.class.getName() + " 的单例已被反序列化破坏");
            }
        }
        objFile.delete();


        // 测试反射破坏
        Constructor<?>[] constructor = Singleton6.class.getDeclaredConstructors();
        constructor[0].setAccessible(true);

        try {
            constructor[0].newInstance("test", 1);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(Singleton6.class.getName() + " 的单例没有被反射破坏");
        }
    }

}
