package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

/**
 * @Desc: 爬楼梯
 * @Auther: dengyanliang
 * @Date: 2023/4/19 20:30
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        System.out.println(climbingStair(2));
        System.out.println(climbingStair(3));
        System.out.println(climbingStair(4));
    }

    /**
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     * 使用数组推导公式计算，不是很常用
     * @param n
     * @return
     */
    private static int climbingStair(int n){
        double sqrt_5 = Math.sqrt(5);
        double fib = (Math.pow((1+sqrt_5)/2,n+1)-Math.pow((1-sqrt_5)/2,n+1))/sqrt_5;
        return (int)fib;
    }

    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     * @param n
     * @return
     */
    private static int climbingStair2(int n){
        int pre2 = 1;
        int pre1 = 1;
        int curr = 0;

        for (int i = 2; i <= n; i++) {
            curr = pre2 + pre1;
            pre2 = pre1;
            pre1 = curr;
        }
        return curr;
    }


    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * @param n
     * @return
     */
    private static int climbingStair1(int n){
        // 定义dp数组，保存当前只有i级台阶时可能的情况
        int[] dp = new int[n+1];
        dp[0] = 1; // 因为dp[2]=2，为了计算dp[2]的值，所以这里设定dp[0]=1
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }


}
