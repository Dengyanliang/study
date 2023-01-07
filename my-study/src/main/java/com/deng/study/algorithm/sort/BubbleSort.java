package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:冒泡排序
 * @Auther: dengyanliang
 * @Date: 2021/1/25 10:57
 */
public class BubbleSort {


    /**
     * 依次比较两个元素的大小，如果当前元素和后面的元素顺序不对，那么就进行交换，一趟走完，最值元素就放在最后位置上了
     *
     * @param arr
     */
    public static void sort(int[] arr){
        boolean flag = false;
        // 总共比较的次数，除去本身之外，共n-1次
        for(int i = 0; i < arr.length-1; i++){
            // 每一趟结束后，最值就在末尾了，所以比较的次数会越来越少
            for(int j = 0; j < arr.length-1-i; j++){
                if(arr[j] > arr[j+1]){
                    flag = true;
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    System.out.println("=====-->"+Arrays.toString(arr));
                }
            }
            if(!flag){ // 在一趟排序中，一次都没有发生交换过，则直接退出，说明原始数组就是有序的
                System.out.println("有序的，直接退出");
                break;
            }
            System.out.println("--->"+Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
//        int[] arr = {2,1,6,3,4,6,9,8};
        int[] arr = {1,2,3,4,5};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
