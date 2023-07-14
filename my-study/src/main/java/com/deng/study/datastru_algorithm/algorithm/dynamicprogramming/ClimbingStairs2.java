package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * @Desc: 爬楼梯：每次可以爬1阶或者3阶
 * @Auther: dengyanliang
 * @Date: 2023/4/19 20:30
 */
public class ClimbingStairs2 {

//    static int[] cache = new int[51];

    public static void main(String[] args) {
//        System.out.println(climbingStair(2));
//        System.out.println(climbingStair(3));
        System.out.println(climbingStair(4));
        System.out.println(climbingStair(50));
    }

    private static int climbingStair(int n){
        int[] cache = new int[n+1];
        Arrays.fill(cache,-1); // 初始化为-1

        cache[0] = 0;
        if(n>=1) cache[1] = 1;
        if(n>=2) cache[2] = 1;
        if(n>=3) cache[3] = 2;
        return recursive(n,cache);
    }

    private static int recursive(int n,int[] cache){
        if(cache[n] != -1)
            return cache[n];
        cache[n] = recursive(n - 1, cache) + recursive(n - 3, cache);
        return cache[n];
    }

    /**
     * 使用动态规划算法
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * @param n
     * @return
     */
    private static int climbingStair1(int n){
        int[] dp = new int[n+1];
        if(n>=1) dp[1] = 1;
        if(n>=2) dp[2] = 1;
        if(n>=3) dp[3] = 2;

        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i-1]+dp[i-3];
        }
        return dp[n];
    }


}
