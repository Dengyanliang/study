package com.deng.study.algorithm.basic;

import com.deng.study.source.StdOut;

/**
 * @Desc:斐波那契
 * @Auther: dengyanliang
 * @Date: 2020/10/12 19:24
 */
public class Fibonacci {

    public static long f2(int n){ // 第二种办法
        int f = 0;
        int g = 1;
        for(int i = 0; i <= n; i++){
            f = f + g;
            g = f - g;
        }
        return g;
    }

    public static long f(int n){
        if(n ==0)
            return 0;
        if(n == 1)
            return 1;
        return f(n-1)+f(n-2);
    }

    public static void main(String[] args) {
//        StdOut.println(f(0));
        StdOut.println(f(6));
        StdOut.println(f2(6));
    }

}
