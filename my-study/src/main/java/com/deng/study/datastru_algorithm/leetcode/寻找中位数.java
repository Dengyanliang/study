package com.deng.study.datastru_algorithm.leetcode;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 寻找中位数 {
    public static void main(String[] args) {
        int[] a = new int[]{1,3};
        int[] b = new int[]{2};

        double medianSortedArrays = findMedianSortedArrays(a, b);
        System.out.println(medianSortedArrays);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] newArray = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;
        while (true) {
            if (i < nums1.length && j < nums2.length) {
                if (nums1[i] >= nums2[j]) {
                    newArray[k] = nums2[j];
                    j++;
                    k++;
                } else {
                    newArray[k] = nums1[i];
                    i++;
                    k++;
                }
            } else {
                break;
            }
        }

        while (i < nums1.length) {
            newArray[k] = nums1[i];
            i++;
            k++;
        }
        while (j < nums2.length) {
            newArray[k] = nums2[j];
            j++;
            k++;
        }

        if (newArray.length % 2 == 0) { // 偶数
            return
                    (double)(newArray[newArray.length / 2] + newArray[(newArray.length / 2) - 1])/2;
        } else { // 奇数
            return (double)newArray[newArray.length / 2];
        }
    }

}
