package com.deng.study.datastru_algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 三数之和 {
    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};

        // 第一种办法：暴力法
        int n = nums.length;
        ArrayList<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < n-2; i++) {
            for (int j = i+1; j < n - 1; j++) {
                for (int k = j+1; k < n; k++) {
                    if(nums[i]+nums[j]+nums[k] == 0)
                        resultList.add(Arrays.asList(nums[i],nums[j],nums[k]));
                }
            }
        }
        System.out.println(resultList);
    }
}
