package com.deng.study.algorithm.sort;

import com.deng.study.util.ArrayUtil;
import java.util.Arrays;

/**
 * @Desc:选择排序
 * @Auther: dengyanliang
 * @Date: 2021/1/26 11:20
 */
public class SelectSort {
    private static int count = 0;

    /**
     * 首先从所有元素中找到最小的元素，放在第一位，然后从剩余的元素的中找到第二小的，放在第二位，依次类推
     * @param arr
     */
    public static void sort(int[] arr){
        for(int i = 0;  i < arr.length-1; i++){ // 最后一次不用再遍历，因为最后一个就是最值元素了，所以用 i < arr.length-1
            int minIndex = i; // 以外层循环的当前索引作为最小值索引
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < arr[i]){
                    minIndex = j;
                }
            }
            System.out.println("--->i：" + i + "，minIndex：" + minIndex);
            if(i != minIndex){
                ArrayUtil.swap(arr,minIndex,i);

                System.out.println("i：" + i + "，minIndex：" + minIndex + "，第" + (++count) + "趟：" + Arrays.toString(arr));
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 7, 3, 1, 6, 9, 8};
        System.out.println("原始数组：" + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }
}
