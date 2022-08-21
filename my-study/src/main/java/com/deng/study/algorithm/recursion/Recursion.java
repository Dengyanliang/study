package com.deng.study.algorithm.recursion;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Desc:递归
 * @Auther: dengyanliang
 * @Date: 2020/10/15 20:16
 */
@Slf4j
public class Recursion {

    @Test
    public void testFun(){
        fun(3);
    }

    /**
     * 使用栈处理递归
     *   语句n1
     *   fun(3) --|---> 语句n1
     *   语句n2   |--->  fun(2) --|---> 语句n1
     *                  语句n2    |--->  fun(1)
     *                            |---> 语句n2
     * @param n
     */
    public void fun(int n){
        if(n >= 1){
            System.out.println("n1=" + n);
            fun(n - 1);
            System.out.println("n2=" + n);
        }
    }

    public static String exR1(int n){
        System.out.println("n:"+n);
        if(n <= 0)
            return "";

//        System.out.println("-----------------");
//
//        String result = exR1(n-3); // 3-3
//        System.out.println("result1:"+result);
//
//        result += n;                  // 3
//        System.out.println("result2:"+result);
//
//        result += exR1(n-2);       // 3-2=1
//        System.out.println("result3:"+result);
//
//        result += n;
//        System.out.println("result4:"+result);
//
//        return result;
        return exR1(n-3)+n+exR1(n-2)+n;
    }

    private static int mystery(int a, int b) {
        if (b == 0)
            return 0;
        if (b % 2 == 0) {
            return mystery(a + a, b / 2);
        }
        int result = mystery(a + a, b / 2);
        return result + a;
    }

    public static void main(String[] args) {
        log.info("exR1(3)：{}",exR1(3));

        log.info("mystery(2,3):{}",mystery(2,3));
    }

}
