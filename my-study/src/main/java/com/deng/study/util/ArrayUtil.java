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

}
