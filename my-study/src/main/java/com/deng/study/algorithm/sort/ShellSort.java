package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:希尔排序
 * 将较大的数据集合分割为若干个小组（逻辑上分组），然后对每一个分组分别进行插入排序，此时，插入排序所作用的数据量比较小（每一个小组），插入的效率比较高
 * @Auther: dengyanliang
 * @Date: 2021/1/27 14:32
 */
public class ShellSort {

    public static void sort(int[] arr){
        int length = arr.length;
        // 进行分组，最开始的分组为数组长度的一半
        for(int gap = length/2; gap > 0; gap /= 2){
            // 对各个分组进行插入排序
            for(int i = gap; i < length; i++){
                // 将arr[i]插入到所在分组的正确位置上
                insertSort(arr,gap,i);
            }
        }
    }

    private static void insertSort(int[] arr,int gap,int i) {
        int element = arr[i];
        int index = i - gap;
        while(index >= 0 && element < arr[index]){ // 需要排序的元素小于有序部分的元素
            arr[index+gap] = arr[index];           // 将大的元素右移
            index -= gap;
        }
        if(index != i-gap){
            arr[index+gap] = element; // +gap的原因是因为当前索引位置的元素已经不大于要排序的元素了，所以+gap存放要排序的元素
        }
    }


    public static void main(String[] args) {
        int[] arr = {2,1,6,3,4,6,9,8};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
