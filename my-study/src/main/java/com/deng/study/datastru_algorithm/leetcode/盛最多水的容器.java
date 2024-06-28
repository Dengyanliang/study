package com.deng.study.datastru_algorithm.leetcode;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 盛最多水的容器 {
    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};
        int i = 0;
        int j = height.length-1;
        int area = 0;
        while(i < j){
            if(height[i] < height[j]){
                area = Math.max(area,  (j-i)*height[i]);
                i++;
            }else{
                area = Math.max(area, (j-i)*height[j]);
                j--;
            }
        }
        System.out.println(area);
    }
}
