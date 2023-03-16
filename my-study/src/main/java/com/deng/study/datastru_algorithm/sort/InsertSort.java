package com.deng.study.datastru_algorithm.sort;

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
    public static void sort(int[] arr) {
        for (int unsortedIndex = 1; unsortedIndex < arr.length; unsortedIndex++) {
            int unsortedElement = arr[unsortedIndex]; // 无序部分的第一个元素，也就是待排序的元素
            int sortedIndex = unsortedIndex - 1;      // 有序部分的最后索引位置

            // 从有序部分的最后一个位置开始遍历，如果有序部分当前位置的元素大于待排序的元素，那么把有序部分的最后一个元素右移，腾出位置，为了放置待排序元素
            for (int i = sortedIndex; i >= 0; i--) {
                if (unsortedElement < arr[i]){
                    arr[i + 1] = arr[i]; // 数据右移
                }else{
                    break;
                }
                sortedIndex--;               // 有序部分索引减-，为了放置待排序元素
            }
            // 如果腾出来的位置+1 不等于 待排序元素，说明原来数据变动过，所以插入元素；否则不插入
            if (sortedIndex != unsortedIndex - 1) {
                arr[sortedIndex + 1] = unsortedElement;
            }
        }
    }

    /**
     * 第二种办法 其实和第一种办法是一样的，只不过一个是for 一个是while
     */
    public static void sort2(int[] arr) {
        for (int unsortedIndex = 1; unsortedIndex < arr.length; unsortedIndex++) {
            int unSortedElement = arr[unsortedIndex];
            int sortedIndex = unsortedIndex - 1;
            while (sortedIndex >= 0 && unSortedElement < arr[sortedIndex]) {  // 需要排序的元素小于有序部分的元素
                arr[sortedIndex + 1] = arr[sortedIndex];  // 将有序部分大的元素右移
                sortedIndex--;
            }
            if (sortedIndex != unsortedIndex - 1) {
                arr[sortedIndex + 1] = unSortedElement;        // +1的原因是因为当前索引位置的元素已经不大于要排序的元素了，所以+1存放要排序的元素
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 6, 3, 4, 6, 9, 8};
        System.out.println(Arrays.toString(arr));
        sort2(arr);
        System.out.println(Arrays.toString(arr));
    }
}
