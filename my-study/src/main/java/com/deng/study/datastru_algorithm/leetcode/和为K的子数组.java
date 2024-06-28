package com.deng.study.datastru_algorithm.leetcode;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 和为K的子数组 {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        int k = 3;

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j >= 0 ; j--) {
                sum += nums[j];
                if(sum == k)
                    count++;
            }
        }
        System.out.println(count);
    }
}
