package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

/**
 * @Desc: 零钱兑换
 * @Auther: dengyanliang
 * @Date: 2023/4/28 11:11
 */
public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{5,1,2},11));
        System.out.println(coinChange(new int[]{2},3));
//        System.out.println(coinChange(new int[]{1},0));
//        System.out.println(coinChange(new int[]{1},1));
//        System.out.println(coinChange(new int[]{1},2));
    }

    /**
     * 暴力法求解
     *  时间复杂度：O(A^N)
     *  空间复杂度：O(A)
     * @param coins
     * @param amount
     * @return
     */
    private static int coinChange1(int[] coins, int amount){
        return coinChangeByCoinIndex(coins,amount,0);
    }

    private static int coinChangeByCoinIndex(int[] coins, int amount, int index){
        // 基准情况
        if(amount == 0) {
            return 0;
        }
        // 不能越界并且金额不能为负数
        if(index >= coins.length || amount < 0) {
            return -1;
        }

        // 当前硬币能取到的最大个数
        int maxCount = amount / coins[index];
        // 保存当前能够凑出amount金额的最小硬币数量
        int minCoinCount = Integer.MAX_VALUE;

        for (int i = 0; i <= maxCount; i++) {
            // 剩余金额
            int restAmount = amount - i * coins[index];
            // 用其他硬币凑剩余金额，递归调用
            int restMinCoinNum = coinChangeByCoinIndex(coins,restAmount,index+1);

            // 如果结果不为-1，说明凑出了amount，比较是否是最小的金额数量
            if(restMinCoinNum != -1){
                int currCoinNum = restMinCoinNum + i;
                minCoinCount = Math.min(currCoinNum, minCoinCount);
            }
        }

        return minCoinCount == Integer.MAX_VALUE ? - 1 : minCoinCount;
    }

    /**
     * 动态规划求解
     *  时间复杂度：O(A*N)
     *  空间复杂度：O(A)
     * @param coins
     * @param amount
     * @return
     */
    private static int coinChange(int[] coins, int amount){

        // dp(i)保存要凑出金额为i的最小硬币数量
        int[] dp = new int[amount+1];
        // 定义初始状态
        dp[0] = 0;

        // 遍历所有可能的金额，状态转移
        for (int i = 1; i <= amount ; i++) {
            int minCoinNum = Integer.MAX_VALUE;
            // 遍历所有硬币，选取扣除当前硬币面值后凑出对应金额用硬币数量最小的那个
            for (int coin : coins) {
                // 保证能够凑出金额的前提下，取最小的数量
                if(i >= coin && dp[i-coin] >= 0){
                    minCoinNum = Math.min(minCoinNum, dp[i-coin] + 1); // 如果加1写在括号外面，那么每次比较都加1结果就不对了
                }
            }
            dp[i] = minCoinNum == Integer.MAX_VALUE ? -1 : minCoinNum;
        }
        return dp[amount];
    }
}
