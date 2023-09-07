package com.deng.study.datastru_algorithm.sort;


import com.deng.common.util.MyArrayUtil;
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
    public static void sort(int[] arr) {
        boolean flag = false;
        // 总共比较的次数，除去本身之外，共n-1次
        for (int i = 0; i < arr.length - 1; i++) {
            // 每一趟结束后，最值就在末尾了，所以比较的次数会越来越少
            for (int j = 0; j < arr.length - 1 - i; j++) {
                System.out.println("第" + (i + 1) + "次冒泡前，第" + (j + 1) + "次比较");
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    System.out.println("第" + (j + 1) + "次交换前：" + Arrays.toString(arr));
                    MyArrayUtil.swap(arr, j, j + 1);
                    System.out.println("第" + (j + 1) + "次交换后：" + Arrays.toString(arr));
                }
            }

            if (!flag) { // 在一趟排序中，一次都没有发生交换过，则直接退出，说明原始数组就是有序的
                System.out.println("有序的，直接退出");
                break;
            }
            System.out.println("第" + (i + 1) + "次冒泡后：" + Arrays.toString(arr));
        }
    }

    /**
     * 更高效的冒泡排序：记录最后一次交换位置的索引
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        int n = arr.length - 1;
        // 总共比较的次数，除去本身之外，共n-1次
        for (int i = 0; i < arr.length - 1; i++) {
            int last = -1;   // 表示最后一次交换索引的位置
            for (int j = 0; j < n; j++) {
                System.out.println("第" + (i + 1) + "次冒泡前，第" + (j + 1) + "次比较");
                if (arr[j] > arr[j + 1]) {
//                    System.out.println("第"+(j+1)+"次交换前："+Arrays.toString(arr));
                    MyArrayUtil.swap(arr, j, j + 1);
//                    System.out.println("第"+(j+1)+"次交换后："+Arrays.toString(arr));
                    last = j;
                }
            }
            n = last;
            System.out.println("n:" + n);

            if (n == -1) { // 在一趟排序中，一次都没有发生交换过，则直接退出，说明原始数组就是有序的
                System.out.println("有序的，直接退出");
                break;
            }
            System.out.println("第" + (i + 1) + "次冒泡后：" + Arrays.toString(arr));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 7, 3, 1, 6, 9, 8};
//        int[] arr = {1,2,3,4,5};
        System.out.println("原始数组：" + Arrays.toString(arr));
        sort2(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }
}
