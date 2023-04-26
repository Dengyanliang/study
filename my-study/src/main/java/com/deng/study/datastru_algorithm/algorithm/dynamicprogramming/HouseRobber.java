package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

/**
 * @Desc: 动态规划-打家劫舍
 * @Auther: dengyanliang
 * @Date: 2023/4/26 18:52
 */
public class HouseRobber {

    public static void main(String[] args) {
        int[] num1 = {1,2,3,1};
        int[] num2 = {2,7,9,3,1};
        int[] num3 = {2,7,1,3,9};

        System.out.println(rob(num1));
        System.out.println(rob(num2));
        System.out.println(rob(num3));

    }

    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * @param nums
     * @return
     */
    private static int rob1(int[] nums){
        int n = nums.length;

        // 定义dp数组，保存只考虑i个房间时能偷到的最大金额
        int[] dp = new int[n+1];

        // 定义初始状态
        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i-1], dp[i-1]);
        }
        return dp[n];
    }
    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     * @param nums
     * @return
     */
    private static int rob(int[] nums){
        int n = nums.length;

        // 定义初始状态
        int pre2 = 0;
        int pre1 = nums[0];
        int curr = 0;

        for (int i = 2; i <= n; i++) {
            curr = Math.max(pre2 + nums[i-1], pre1);
            pre2 = pre1;
            pre1 = curr;
        }
        return curr;
    }
}
