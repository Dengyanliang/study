package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * @Desc: 买卖股票最佳时机
 * @Auther: dengyanliang
 * @Date: 2023/4/19 21:01
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices1 = {7,1,5,3,6,4};
        int[] prices2 = {7,6,4,3,1};
        int[] prices3 = {2,7,5,9,1,4,3};
        System.out.println(maxProfit(prices1));
        System.out.println(maxProfit(prices2));
        System.out.println(maxProfit(prices3));
    }

    /**
     * 暴力法，遍历所有可能的买入卖出点
     *
     * @param prices
     * @return
     */
    private static int maxProfit1(int[] prices){
        int n = prices.length;

        // 定义当前的最大利润值
        int maxProfit = 0;

        // i<n-1 是因为n是最后一个值时，没有卖出的可能性，就没有必要再去处理了
        for (int i = 0; i < n - 1; i++) {
            for (int j = i; j < n; j++) {
                int currProfit = prices[j] - prices[i];
                maxProfit = Math.max(maxProfit,currProfit);
            }
        }

        return maxProfit;
    }

    /**
     * 使用动态规划解决
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param prices
     * @return
     */
    private static int maxProfit2(int[] prices){
        int n = prices.length;

        // 定义当前的最大利润值
        int maxProfit = 0;

        // 用dp数组保存截止每一天的历史最低价格
        int[] dp = new int[n+1];
        dp[0] = Integer.MAX_VALUE; // 初始值给最大，方便更新最小值

        for (int i = 1; i <= n; i++) {
            dp[i] = Math.min(dp[i-1],prices[i-1]); // keypoint 滚动
        }
        System.out.println("原始数组：" + Arrays.toString(prices));
        System.out.println("dp数组：" + Arrays.toString(dp));

        // 利用历史最低价格，得到每一天卖出的最大利润
        for (int i = 1; i <= n; i++) {
            int currProfit = prices[i-1] - dp[i];
            System.out.print(currProfit + " ");
            maxProfit = Math.max(maxProfit,currProfit);
        }
        System.out.println();
        return maxProfit;
    }

    /**
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param prices
     * @return
     */
    private static int maxProfit(int[] prices){
        int n = prices.length;

        // 定义当前的最大利润值
        int maxProfit = 0;

        // 保存当前的历史最低价格
        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minPrice = Math.min(minPrice,prices[i]);

            // 利用历史最低价格，得到每一天卖出的最大利润
            int currProfit = prices[i] - minPrice;
            System.out.print(currProfit + " ");
            maxProfit = Math.max(maxProfit,currProfit);
        }

        System.out.println();
        return maxProfit;
    }
}
