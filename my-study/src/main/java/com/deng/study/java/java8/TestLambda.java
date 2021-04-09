package com.deng.study.java.java8;

import java.util.Comparator;
import java.util.function.Consumer;

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
     * 没有参数，没有返回值
     * () ->
     */
    private static void test1(){
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
        Comparator<Integer> comparator = (x,y) -> {
          System.out.println("函数式接口:");
          return Integer.compare(x,y);
        };

        int result = comparator.compare(1,3);
        System.out.println("result: " + result);

        // 这里(x,y) 不用写数据类型，是因为omparator<Integer>中已经指定了Integer了
        Comparator<Integer> comparator2 = (x,y) -> Integer.compare(x,y);
        result = comparator2.compare(5,3);
        System.out.println("result: " + result);

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
