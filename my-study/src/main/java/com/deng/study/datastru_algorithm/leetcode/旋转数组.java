package com.deng.study.datastru_algorithm.leetcode;

import java.util.Arrays;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 旋转数组 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        int k = 3;
        int n = nums.length;
        int[] newArr = new int[n];

        for (int i = 0; i < n; i++) {
            int index = (i + k) % n ;
            System.out.print(index + "\t");
            newArr[index] = nums[i];
        }
        System.out.println();
        System.out.println(Arrays.toString(newArr));
        System.arraycopy(newArr, 0, nums, 0, n);
    }
}
