package com.deng.study.datastru_algorithm.sort;

import java.util.Arrays;

/**
 * @Desc:希尔排序 将较大的数据集合分割为若干个小组（逻辑上分组），然后对每一个分组分别进行插入排序，
 * 此时，插入排序所作用的数据量比较小（每一个小组），插入的效率比较高
 * @Auther: dengyanliang
 * @Date: 2021/1/27 14:32
 */
public class ShellSort {

    public static void sort(int[] arr) {
        int length = arr.length;
        int count = 0;
        // 进行分组，最开始的分组为数组长度的一半
        for (int gap = length / 2; gap > 0; gap /= 2) {
            // 对各个分组进行插入排序
            for (int unSortedIndex = gap; unSortedIndex < length; unSortedIndex += gap) {
                // 将arr[i]插入到所在分组的正确位置上
                count++;
                System.out.println("gap:"+gap+"，count:" + count+"，插入前:" + Arrays.toString(arr));
                insertSort(arr, gap, unSortedIndex);
                System.out.println("gap:"+gap+"，count:" + count+"，插入后:" + Arrays.toString(arr));
                System.out.println();
            }
//            System.out.println(Arrays.toString(arr));
        }
    }

    private static void insertSort(int[] arr, int gap, int unSortedIndex) {
        int unSortedElement = arr[unSortedIndex];
        int sortedIndex = unSortedIndex - gap;
        while (sortedIndex >= 0 && unSortedElement < arr[sortedIndex]) { // 需要排序的元素小于有序部分的元素
            arr[sortedIndex + gap] = arr[sortedIndex];           // 将大的元素右移
            sortedIndex -= gap;
        }
        if (sortedIndex != unSortedIndex - gap) {
            System.out.println("待排序元素：" + unSortedElement);
            arr[sortedIndex + gap] = unSortedElement; // +gap的原因是因为当前索引位置的元素已经不大于要排序的元素了，所以+gap存放要排序的元素
        }
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,5};
        int[] arr = {5, 2, 7, 3, 1, 6, 9, 8};
        System.out.println("原始数组：" + Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

}
