package com.deng.study.datastru_algorithm.leetcode;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 最大子数组和 {

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        // 定义动态规划数组
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if(dp[i-1] > 0){ // 如果dp[i-1]>0，那么可以把nums[i]直接接在dp[i-1]表示的那个数组的后面，得到和更大的连续子数组
                dp[i] = dp[i-1] + nums[i];
            }else{  // 如果dp[i-1]<=0，那么nums[i]加上前面的数dp[i-1]以后，值一定不会变大。于是dp[i]另起炉灶，此时单独的一个nums[i]的值，就是dp[i]
                dp[i] = nums[i];
            }
        }

        int result = dp[0];
        for (int i = 1; i < nums.length; i++) {
            result = Math.max(result,dp[i]);
        }
        System.out.println(result);
    }
}
