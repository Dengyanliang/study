package com.deng.study.algorithm.search;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Desc:二分查找
 * @Auther: dengyanliang
 * @Date: 2020/9/20 21:53
 */
@Slf4j
public class BinarySearch {

    /**
     * 使用循环的方式
     * @param array
     * @param key
     * @return
     */
    public static int rank(int[] array,int key){
        int low = 0;
        int height = array.length - 1; // 数组的索引，最高位为长度减1
        while(low <= height){      // 当low和height相等的时候，才能找到数据
            int middle = low + (height - low) / 2; // 在数学上等价于(height +low) / 2，但是后者会引起结果溢出，所以使用前者
//            int middle = (height +low) / 2;
            log.info("low:{},middle:{},height:{}",low,middle,height);
            if(key < array[middle])
                height = middle - 1;
            else if(key > array[middle])
                low = middle + 1;
            else
                return middle;
        }
        return -1;
    }

    /**
     * 使用递归的方式
     * @param array
     * @param key
     * @param left
     * @param right
     * @return
     */
    public static int rank(int[] array,int key,int left,int right){
        if(left > right){
            return -1;
        }
        int middle = left + (right-left)/2;
        if(key < array[middle]){
            return rank(array,key,left,middle-1);
        }else if(key > array[middle]){
            return rank(array,key,middle+1,right);
        }else{
            return middle;
        }
    }


    /**
     * 返回相同数据的集合索引
     * @param array
     * @param key
     * @param left
     * @param right
     * @return
     */
    public static List<Integer> rank2(int[] array,int key,int left,int right){
        List<Integer> list = null;
        if(left > right){
            return list;
        }
        int middle = left + (right-left)/2;
        if(key < array[middle]){
            return rank2(array,key,left,middle-1);
        }else if(key > array[middle]){
            return rank2(array,key,middle+1,right);
        }else{
            list = new ArrayList<>();
            // 向左遍历
            int start = middle-1;
            while(start >= 0 && array[start] == key){
                list.add(start);
                start--;
            }
            list.add(middle);
            int end = middle+1;
            // 向左遍历
            while(end <= array.length-1 && array[end] == key){
                list.add(end);
                end++;
            }

            return list;
        }
    }

    public static void main(String[] args) {
        int[] array = {1,3,4,10,7,8};
        int num = new Random().nextInt(1000000);
        System.out.println("num:" + num);
//
//        int[] array = new int[100000];
//        for(int i = 0; i < array.length;i++ ){
//            array[i] = new Random().nextInt(1000000);
//        }

        Arrays.sort(array);
        int key = 7;
        int result = rank(array,key);
        System.out.println("第一种方法："+result);

        result = rank(array,key,0,array.length-1);
        System.out.println("第二种方法："+result);

        int[] array2 = {1,3,4,4,4,4,10,7,8};
        Arrays.sort(array2);
        List<Integer> resultList = rank2(array2,4,0,array.length-1);
        System.out.println("resultList:"+resultList);

        double d = 1.0 / 0.0 ;
        double d2 = -1.0 /0.0;
        System.out.println(d);
        System.out.println(d2);
    }
}
