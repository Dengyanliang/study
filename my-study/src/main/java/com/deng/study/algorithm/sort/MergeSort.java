package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:归并排序
 *       采用分治法，将原始数组进行分割，先使每个子数组排序，然后再将每个子数组进行合并，最终得到有序的数组
 * @Auther: dengyanliang
 * @Date: 2021/1/26 12:46
 */
public class MergeSort {

    private static int count = 0;

    public static void sort(int[] arr){
        int[] tempArr = new int[arr.length];
        merge(arr,tempArr,0,arr.length-1);
    }

    /**
     *
     * @param arr      原始数组
     * @param tempArr  临时数组
     * @param start    开始位置
     * @param end      结束位置
     */
    private static void merge(int[] arr, int[] tempArr,int start,int end){
        int middle = start + (end-start)/2; // 避免数组越界
        if(start < end){
            merge(arr,tempArr,start,middle);
            merge(arr,tempArr,middle+1,end);
            mergeSort(arr,tempArr,start,middle+1,end);
        }
    }

    /**
     *
     * @param arr           原始数组
     * @param tempArr       临时数组
     * @param leftStart     左边数组开始位置
     * @param rightStart    右边数组开始位置
     * @param rightEnd      右边数组结束位置
     */
    private static void mergeSort(int[] arr, int[] tempArr,int leftStart,int rightStart,int rightEnd){
        int index = leftStart;        // 开始位置
        int leftEnd = rightStart - 1; // 左边数组结束位置
        while(leftStart <= leftEnd && rightStart <= rightEnd){
            if(arr[leftStart] < arr[rightStart]){
                tempArr[index++] = arr[leftStart++];
            }else{
                tempArr[index++] = arr[rightStart++];
            }
        }
        while(leftStart <= leftEnd){
            tempArr[index++] = arr[leftStart++];
        }

        while(rightStart <= rightEnd){
            tempArr[index++] = arr[rightStart++];
        }

//        System.out.println("index:" + index +", leftStart:"+leftStart +", rightEnd:"+rightEnd);

        System.out.println("--"+(++count)+"--" + Arrays.toString(tempArr));

        int length = rightEnd - leftStart + 1; // 总的数组长度
        for(int i = 0; i < length; i++,rightEnd--){
            arr[rightEnd] = tempArr[rightEnd];     // 从后往前拷贝
        }
    }


    public static void main(String[] args) {
        int[] arr = {2,4,1,9,8,5,4,6};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
