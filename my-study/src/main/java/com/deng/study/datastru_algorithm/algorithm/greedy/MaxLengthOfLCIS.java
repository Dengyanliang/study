package com.deng.study.datastru_algorithm.algorithm.greedy;

import org.junit.jupiter.api.Test;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/7 15:54
 */
public class MaxLengthOfLCIS {

    @Test
    public void test1() {
        int[] nums = {1, 3, 5, 4, 7};
        int ans = 0; // 最大的递增值
        int start = 0; // 起始位置
        for (int i = 0; i < nums.length; i++) {
            // 从第二个元素开始，如果发现当前元素值小于等于前一个元素值，表明不是增长序列。所以新的其实位置就是当前位置
            if (i > 0 && nums[i] <= nums[i - 1]) {
                start = i;
            }
            ans = Math.max(ans, i - start + 1);
        }
        System.out.println(ans);
    }

    @Test
    public void test2() {
        int[] nums = {1, 3, 5, 4, 7};
        int ans = 1;  // 最大的递增值
        int count = 1; // 起始位置
        for (int i = 0; i < nums.length-1; i++) {
            // 如果当前值比下一个值大，则递增
            if(nums[i+1] > nums[i]){
                count++;
            }else{
                count=1; // 否则，从头开始
            }
            ans = Math.max(ans,count);
        }

        System.out.println(ans);
    }

}
