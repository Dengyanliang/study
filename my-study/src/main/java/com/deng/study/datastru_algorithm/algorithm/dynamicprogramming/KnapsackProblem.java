package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;


import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: 动态规划算法
 * @Auther: dengyanliang
 * @Date: 2023/3/1 15:42
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        int[] weights = {1,4,3}; // 物品的重量
        int[] values = {1500,3000,2000}; // 物品的价值 v[i]
        int n = weights.length; // 物品的个数
        int capacity = 4; // 背包能放入东西的最大重量

        // 创建二维数组
        // v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] dp = new int[n+1][capacity+1];

        // 为了记录放入商品的情况，我们定一个二维数组
        int[][] path = new int[n+1][capacity+1];

        // 遍历所有可能的物品个数，以及背包容量
        for (int i = 1; i <= n; i++) { // 不处理第一行
            for (int j = 1; j <= capacity; j++) { // 不处理第一列
                System.out.println("---->" + j + "," + weights[i-1]);
                if(j >= weights[i-1]){
                    System.out.println("j>=weights[i-1]时 dp["+(i-1)+"]["+(j-weights[i-1])+"]：" + dp[i-1][j-weights[i-1]] + ", values["+(i-1)+"]：" + values[i-1]);
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]);
                    path[i][j] = 1;
                }else{
                    System.out.println("j<weights[i-1]时 dp["+(i-1)+"]["+j+"]：" + dp[i-1][j]);
                    dp[i][j] = dp[i-1][j];
                }
                System.out.println("    dp["+i+"]["+j+"]：" + dp[i][j]);
            }
        }
        System.out.println("======dp=======" + dp[n][capacity]);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j]+ " ");
            }
            System.out.println();
        }

        System.out.println("======path=======");
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                System.out.print(path[i][j]+ " ");
            }
            System.out.println();
        }

        // 输出最后放入的是哪些商品
        // 通过path，这样输出会把所有的放入情况都得到，其实只需要得到最后的放入
//        for (int i = 0; i < path.length; i++) {
//            for (int j = 0; j < path[i].length; j++) {
//                if(path[i][j]== 1){
//                    System.out.println("第"+i+"个商品放入到背包");
//                }
//            }
//        }

        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) { // 从path最后开始找
                System.out.println("第" + i + "个商品放入到背包");
                j = j - weights[i-1];
            }
            i--;
        }

        // 第二种获取背包索引的方法
        List<Integer> result = new ArrayList<>();
        int restCap = capacity;
        for (int k = n; k > 0; k--) {
            if(dp[k][restCap] > dp[k-1][restCap]){
                result.add(k);
                restCap -= weights[k-1];
            }
        }
        System.out.println("商品放入到背包的索引为："+result);
    }
}

