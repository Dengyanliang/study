package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:插入排序
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
            int min = arr[i];
            int indexOfMin = i;
            for(int j = i + 1; j < arr.length; j++){
                if(arr[j] < min){
                    min = arr[j];
                    indexOfMin = j;
                }
            }
            if(i != indexOfMin){
                int temp = arr[indexOfMin];
                arr[indexOfMin] = arr[i];
                arr[i] = temp;

                System.out.print("第"+(++count)+"趟："+Arrays.toString(arr));
                System.out.println();
            }
        }
    }



    public static void main(String[] args) {
        int[] arr = {2,1,9,8,10,5};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
