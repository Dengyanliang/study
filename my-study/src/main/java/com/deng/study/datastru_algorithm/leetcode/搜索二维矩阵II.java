package com.deng.study.datastru_algorithm.leetcode;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 搜索二维矩阵II {

    public static void main(String[] args) {
        int[][] maxtrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int target = 20;

        boolean flag = false;
        int n = maxtrix.length, m = maxtrix[0].length;
        int x = 0, y = m - 1;

        while (x < n && y >= 0){
            if(target == maxtrix[x][y]){
                flag = true;
            }
            if(target > maxtrix[x][y]){
                x++;
            }else{
                y--;
            }
        }
        System.out.println(flag);
    }
}
