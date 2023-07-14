package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;

import org.junit.Test;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/13 20:16
 */
public class DynamicProgrammingTest {

    /**
     * 该题比较迷惑人
     */
    @Test
    public void 翻牌求最大分(){
        String str = "1,-5,-6,4,7,2,-2";
        Integer[] nums = Arrays.stream(str.split(",")).
                map(Integer::parseInt).toArray(Integer[]::new);

        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if(i == 0){
                dp[0] = Math.max(0,nums[0]);
            }else if(i < 3){
                dp[i] = Math.max(0,dp[i-1] + nums[i]);
            }else{
                dp[i] = Math.max(dp[i-3],dp[i-1] + nums[i]);
            }
        }
         System.out.println(dp[n-1]);
    }

    /**
     * 该题有难度
     */
    @Test
    public void 堆中剩余数字(){
        String str = "5 10 20 50 85 1";
        Integer[] nums = Arrays.stream(str.split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(getResult_堆中剩余数字(nums));
    }

    private String getResult_堆中剩余数字(Integer[] arr) {
        int n = arr.length;

        // 构造动态规划数组
        int[] dp = new int[n];
        dp[0] = arr[0];
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i-1] + arr[i];
        }
        System.out.println(Arrays.toString(dp));

        for (int i = 1; i < n; i++) {
            if(dp[i-1] == arr[i]){
                arr[i] *= 2;
                Arrays.fill(arr, 0, i, 0);
                Arrays.fill(dp, 0, i, 0);
                continue;
            }

            if(dp[i-1] > arr[i]){
                int preSum = dp[i-1] - arr[i];
                for (int j = 0; j < i - 1; j++) {
                    if(dp[j] == preSum){
                        arr[i] *= 2;
                        Arrays.fill(arr, j + 1, i, 0);
                        Arrays.fill(dp, j + 1, i, dp[j]);
                        break;
                    }
                    if(dp[j] > preSum){
                        break;
                    }
                }
            }
        }

        StringJoiner sj = new StringJoiner(" ");
        for (int i = n-1; i >=0; i--) {
            if(arr[i] != 0){
                sj.add(arr[i] +"");
            }
        }
        return sj.toString();
    }
}