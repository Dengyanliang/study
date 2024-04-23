package com.deng.study.datastru_algorithm.recursion;


import com.alibaba.fastjson.JSON;
import com.deng.common.source.StdOut;
import org.junit.jupiter.api.Test;


/**
 * @Desc:斐波那契问题
 * @Auther: dengyanliang
 * @Date: 2020/10/12 19:24
 */
public class Fibonacci {

    /**
     * 给定一个数，通过斐波那契计算是否有两个数的值等于给定的数
     */
    @Test
    public void testProd(){
        int prod = 714; // 800;
//        int prod = 800; // 800;
        long[] result = new long[3];

        long s1 = 0;
        long s2 = 0;
        long temp = -1; // 用于保存f(n+1)的值，避免下一次循环的过程中再次计算

        for(int i = 3; ;i++){
            s1 = temp == -1 ? f1(i) : temp;
            s2 = f1(i+1);

            result[0] = s1;
            result[1] = s2;

            if(s1 * s2 == prod) {
                result[2] = 1;
                break;
            }

            if(s1 * s2 > prod){
                result[2] = -1;
                break;
            }
            temp = s2;
        }
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 使用递归处理
     * 时间复杂度 O(2^n)
     * 空间复杂度 O(2^n)
     * @param n
     * @return
     */
    private static long f1(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return f1(n - 1) + f1(n - 2);
        }
    }

    /**
     * 使用动态规划处理
     * 时间复杂度 O(n)
     * 空间复杂度 O(n)
     * @param n
     * @return
     */
    private static long f2(int n){
        int[] dp = new int[n];
        dp[0]= dp[1] = 1;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1]; // 数组是从0下标开始的，所以第n个数，存放在数组的n-1位置上
    }


    /**
     * 使用动态规划处理：只用保留当前n之前的两个数即可
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     * @param n
     * @return
     */
    private static long f3(int n){
        // 定义两个变量，分别保存之前的两个状态
        int pre2 = 1, pre1 = 1;
        int curr = 0;
        for (int i = 2; i < n ; i++) {
            curr = pre1 + pre2;
            pre2 = pre1; // 把pre1赋值给pre2，把pre2覆盖掉
            pre1 = curr; // 把curr赋值给pre1，把pre1覆盖掉
        }
        return curr;
    }

    /**
     * 使用动态规划处理
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     * @param n
     * @return
     */
    private static long f4(int n) {
        int pre2 = 1; // f(0)
        int pre1 = 1; // f(1)
        for (int i = 2; i < n; i++) {
            pre1 = pre1 + pre2; // 计算出当前值
            pre2 = pre1 - pre2; // 计算出前一个值
//            System.out.println("f:" + f +",g:" + g);
        }
        return pre1;
    }

    public static void main(String[] args) {
        // 1 1 2 3 5 8 13
        StdOut.println(f1(6));
        StdOut.println(f2(6));
        StdOut.println(f3(6));
        StdOut.println(f4(6));
    }

}
