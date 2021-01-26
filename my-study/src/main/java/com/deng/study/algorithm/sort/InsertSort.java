package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:插入排序
 * @Auther: dengyanliang
 * @Date: 2021/1/26 10:28
 */
public class InsertSort {


    /**
     * 首先选择第一个数作为有序部分，从第二个元素开始到结尾作为无序部分。从无序部分的第一个元素开始，和有序部分的最后一个元素向前进行比较，直到找到正确的位置为准
     *
     * @param arr
     */
    public static void sort(int[] arr){
        for(int unsortedIndex = 1; unsortedIndex < arr.length; unsortedIndex++){
//            insertSort(arr[unsortedIndex],arr,0,unsortedIndex-1);
            int sortedIndex = unsortedIndex - 1;
            int unsorted = arr[unsortedIndex];
            for(int index = sortedIndex; index >= 0; index--){
                if(arr[unsortedIndex] < arr[index]){
                    arr[index+1] = arr[index];
                    sortedIndex--;
                }
            }
            if(sortedIndex != unsortedIndex){
                arr[sortedIndex+1] = unsorted;
            }
        }
    }

    /**
     * 第二种办法
     * @param element  待排序的元素
     * @param arr      原始数组
     * @param first    有序数组的开始索引
     * @param end      有序部分的结束索引
     */
    private static void insertSort(int element,int[] arr,int first,int end){
        int index = end;
        while(index >= first && element < arr[index]){  // 需要排序的元素小于有序部分的元素
            arr[index+1] = arr[index];  // 将大的元素右移
            index--;
        }
        if(index != end){
            arr[index+1] = element;        // +1的原因是因为当前索引位置的元素已经不大于要排序的元素了，所以+1存放要排序的元素
        }
    }


    public static void main(String[] args) {
        int[] arr = {2,1,6,3,4,6,9,8};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
