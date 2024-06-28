package com.deng.study.datastru_algorithm.leetcode;

import java.util.Arrays;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 除自身以外数组的乘积 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        int n = nums.length;
        // 定义左右数组
        int[] L = new int[n];
        int[] R = new int[n];


        int[] answer = new int[n];

        // L[i]为索引i左侧所有元素的乘积，对于索引为0的元素，因为左侧没有元素，所以L[0]=1
        L[0] = 1;
        for (int i = 1; i < n; i++) {
            L[i] = L[i-1] * nums[i-1];
        }

        // R[i]为索引i右侧所有元素的成绩，对于索引为[n-1]的元素，因为右侧没有元素，所以R[n-1]=1
        R[n-1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            R[i] = R[i+1] * nums[i+1];
        }

        for (int i = 0; i < n; i++) {
            answer[i] = L[i] * R[i];
        }
        System.out.println(Arrays.toString(answer));
    }
}
