package com.deng.study.java.java8.lambda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Desc:lambda表达式
 *  -> 将 lambda表达式分为左右两侧，
 *  左边：参数列表，
 *  右边：lambda表达式体，就是将要实现的功能
 *
 *  lambda表达式的参数列表中的数据类型可以省略不写，因为jvm编译器通过上下文可以推断出数据类型，即"类型推断"
 *  如果要写数据类型，则都需要写（Integer x, Integer y ） -> Integer.compare(x,y);
 *
 *  左右遇一括号省
 *  左侧推断类型省
 *
 *  @FunctionalInterface 是用于标记某个接口是一个函数式接口，该接口中只能有一个方法
 *
 * @Auther: dengyanliang
 * @Date: 2021/4/9 21:46
 */
public class TestLambda {

    /**
     * 测试consumer 接收一个参数，没有返回值
     */
    @Test
    public void testConsumer(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        // 原始做法
        forEach(list, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.print(integer + 1 + " ");
            }
        });
        System.out.println();


        forEach(list,  x -> System.out.print(x+1 + " "));
        System.out.println();
    }

    public static <T> void forEach(List<T> list,Consumer<T> consumer){
        for (T t : list){
            consumer.accept(t);
        }
    }


    @Test
    public void testFunction(){
        // 原始做法：匿名内部类
        List list2 = map(Arrays.asList("helloo", "world", "zhangsan"), new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                return s.length();
            }
        });
        System.out.println("list2:" + list2);

        // lambda做法
        List list = map(Arrays.asList("helloo","world","zhangsan"),(s) -> s.length());
        System.out.println(list);

        // 实例方法引用
        List list3 = map(Arrays.asList("helloo","world","zhangsan"),String::length);
        System.out.println(list3);


        // test andThen
        Function<Integer,Integer> f = (x) -> x + 1;
        Function<Integer,Integer> g = (x) -> x * 2;
        Function<Integer,Integer> h = f.andThen(g);
        int result = h.apply(1); // 类似于数学中的 g(f(x))
        System.out.println("andThen:"+result);

        // test compose
        Function<Integer,Integer> w = f.compose(g);
        result = w.apply(1);    // 类似于数学中f(g(x))
        System.out.println("andThen:"+result);


    }

//    public static List map(List list,Function function){                  // 只能接收单个对象
    public static <T,R> List<R> map(List<T> list,Function<T,R> function){   // 使用泛型处理
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }



    /**
     * 没有参数，没有返回值
     * () ->
     */
    private static void test1(){
        // 原始做法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!");
            }
        };
        runnable.run();

        System.out.println("-------");

        Runnable r = () -> System.out.println("hello");
        r.run();
    }

    /**
     * 有一个参数，没有返回值，左侧小括号可以不写
     * (x) ->
     */
    private static void test2(){
        // 原始做法
        Consumer<String> cons = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s+",---->你好");
            }
        };
        cons.accept("王五");

        Consumer<String> consumer = (s) -> System.out.println(s+",你好");
        consumer.accept("张三");

        // 左侧小括号省略了
        Consumer<String> consumer2 = x -> System.out.println(x+",hello");
        consumer2.accept("李四");
    }


    /**
     * 如果有多个参数，有返回值，并且lambda中有多条语句，则必须使用{}和return
     * 如果lambda中只有一条语句，{}和return 都可以省略
     */
    private static void test3(){
        // 原始写法
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        int result = comparator1.compare(1,3);
        System.out.println("result1---->:" + result);

        // lambda写法
        Comparator<Integer> comparator2 = (x,y) -> {
          System.out.println("函数式接口:");
          return Integer.compare(x,y);
        };

        result = comparator2.compare(1,3);
        System.out.println("result2: " + result);

        // 这里(x,y) 不用写数据类型，是因为omparator<Integer>中已经指定了Integer了
        Comparator<Integer> comparator3 = (x,y) -> Integer.compare(x,y);
        result = comparator3.compare(5,3);
        System.out.println("result3: " + result);

        // 类的静态方法引用
        Comparator<Integer> comparator4 = Integer::compare;
        result = comparator4.compare(5,3);
        System.out.println("result4: " + result);

//        String[] strs;
//        strs = {"a","b"};// 拆开写会报错，不拆开不会报错，这是通过上下文推断出来的

    }

    private static void test4(){
        int result = operate(10,(x) -> x * x);
        System.out.println("result:" + result);

        result = operate(100, y -> y + 20);
        System.out.println("result2:" + result);

        result = operate(20, new MyFun() {
            @Override
            public Integer getValue(Integer num) {
                return num + 10; // 这种写法太死板了，只能实现一种功能
            }
        });
        System.out.println("result3:" + result);
    }

    private static Integer operate(Integer num, MyFun myFun){
        return myFun.getValue(num);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }
}
