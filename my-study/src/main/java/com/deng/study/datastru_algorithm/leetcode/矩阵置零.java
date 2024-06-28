package com.deng.study.datastru_algorithm.leetcode;

import com.deng.common.util.MyArrayUtil;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 矩阵置零 {
    public static void main(String[] args) {
        int[][] matrix = {{1,1,1},{1,0,1},{1,1,1}};
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(matrix[i][j] == 0){
                    row[i] = col[j] = true;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }

        MyArrayUtil.printArray(matrix);
    }
}
