package com.deng.study.datastru_algorithm.leetcode;

import com.deng.common.util.MyArrayUtil;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 旋转图像 {

    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int n = matrix.length;

        // 先水平翻转
        for (int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-i-1][j];
                matrix[n-i-1][j] = temp;
            }
        }

        MyArrayUtil.printArray(matrix);

        System.out.println("---------------");

        // 按照主对角线对折
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j]  = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        MyArrayUtil.printArray(matrix);
    }
}
