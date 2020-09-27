package com.deng.study.algorithm.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/9/26 17:40
 */
@Slf4j
public class ArrayTest {

    // 将数组倒序输出
    private static void reverse(int[] array){
        int N = array.length;
        int temp;
        for(int i = 0; i < N/2; i++){
            temp = array[N-1-i];
            array[N-1-i]=array[i];
            array[i] = temp;
        }
    }


    private static void matrix(int[][] a, int[][] b){
        int N = a.length;
        int[][] c = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                for(int k=0; k < N; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c.length; j++) {
                log.info("i={},j={},c[i][j] :{}" ,i,j,c[i][j]);
            }
        }

    }


    public static void main(String[] args) {
        int[] array = {1,9,10,3,6,8};
        reverse(array);
        log.info("array:{}",array);

        int[][] a = {{1,9,6},{10,3,6},{6,8,6}};
        int[][] b = {{1,9,6},{10,3,6},{6,8,6}};
        matrix(a,b);

    }
}
