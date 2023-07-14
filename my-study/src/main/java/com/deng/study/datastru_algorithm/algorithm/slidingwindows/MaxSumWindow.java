package com.deng.study.datastru_algorithm.algorithm.slidingwindows;

import java.util.Scanner;

/**
 * @Desc: 求滑动窗口内的最大和
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/12 18:13
 */
public class MaxSumWindow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = scanner.nextInt();
//        }
//        int m = scanner.nextInt();

        int n = 6; // 数组长度
        int[] arr = new int[]{10,20,30,15,23,12}; // 数组的值
        int m = 3; // 滑动窗口的大小

        int maxSum = getMaxSum(n, arr, m);
        System.out.println(maxSum);
    }

    private static int getMaxSum(int n, int[] arr, int m) {
        int sum = 0;
        // 初始化第一个窗口的三个值的和
        for (int i = 0; i < m; i++) {
            sum = sum + arr[i];
        }

        int ans = sum;
        // 从数组坐标为i=1，也就是从第二个窗口开始计算求和，最多到5的位置。i+3-1=5，所以i的值最大是3。因为n-m=3，所以i最大值就是n-m
        for (int i = 1; i <= n-m; i++) {
            sum = sum + arr[i+m-1] - arr[i-1];
            ans = Math.max(ans,sum);
        }

        return ans;
    }


}
