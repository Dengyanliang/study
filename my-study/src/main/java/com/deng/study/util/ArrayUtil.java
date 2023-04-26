package com.deng.study.util;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/1/16 20:14
 */
public class ArrayUtil {

    public static void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void printArray(double[][] array){
        // 打印单价数组的结果
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void printArray(int[][] array){
        // 打印单价数组的结果
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
