package com.deng.study.datastru_algorithm.linear;

import com.deng.study.source.StdOut;
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

    private static int[][] addToArray(int M,int N){
       int[][] array = new int[M][N];
       for(int i = 0 ; i < M ; i++){
           for(int j = 0 ; j < N; j++){
               array[i][j] = i+j;
               StdOut.print(array[i][j]+" ");
//               log.info("i:{},j:{},array[{}][{}]:{}",i,j,i,j,array[i][j]);
           }
           StdOut.println();
       }
       return array;
    }

    private static void getArray(int[][] array){
        for(int i = 0 ; i < array.length ; i++){
            for(int j = 0 ; j < array[0].length; j++){
                StdOut.print(array[i][j]+" ");
//                log.info("i:{},j:{},array[{}][{}]:{}",i,j,i,j,array[i][j]);
            }
            StdOut.println();
        }
    }

    private static void reverseArray(int[][] array){
        log.info("i:{},j:{}",array[0].length,array.length);

        for(int i = 0 ; i < array[0].length ; i++){
            for(int j = 0 ; j < array.length; j++){
                StdOut.print(array[j][i]+" ");
//                log.info("i:{},j:{},array[{}][{}]:{}",i,j,i,j,array[j][i]);
            }
            StdOut.println();
        }
    }



    public static void main(String[] args) {
//        int[] array = {1,9,10,3,6,8};
//        reverse(array);
//        log.info("array:{}",array);
//
//        int[][] a = {{1,9,6},{10,3,6},{6,8,6}};
//        int[][] b = {{1,9,6},{10,3,6},{6,8,6}};
//        matrix(a,b);

        int[][] array = addToArray(2,3);
        System.out.println("====");
        getArray(array);
        System.out.println("******");
        reverseArray(array);

    }
}
