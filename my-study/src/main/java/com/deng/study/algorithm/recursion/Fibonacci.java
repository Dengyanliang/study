package com.deng.study.algorithm.recursion;

import com.deng.study.source.StdOut;

/**
 * @Desc:斐波那契问题
 * @Auther: dengyanliang
 * @Date: 2020/10/12 19:24
 */
public class Fibonacci {

    private static long f(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return f(n - 1) + f(n - 2);
        }
    }

    private static long forF(int n) {
        int f = 0;
        int g = 1;
        for (int i = 0; i < n; i++) {
            f = f + g; // 计算出当前值
            g = f - g; // 计算出前一个值
            System.out.println("f:" + f + ",g:" + g);
        }
        return f;
    }

    public static void main(String[] args) {
        // 1 1 2 3 5 8 13
        StdOut.println(f(6));
        StdOut.println(forF(6));
    }

}
