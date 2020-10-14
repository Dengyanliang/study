package com.deng.study.algorithm.search;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Desc:二分查找
 * @Auther: dengyanliang
 * @Date: 2020/9/20 21:53
 */
@Slf4j
public class BinarySearch {

    public static int rank(int key, int[] a){
        int low = 0;
        int height = a.length - 1; // 数组的索引，最高位为长度减1
        while(low <= height){      // 当low和height相等的时候，才能找到数据
            int middle = low + (height - low) / 2; // 在数学上等价于(height +low) / 2，但是后者会引起结果溢出，所以使用前者
//            int middle = (height +low) / 2;
            log.info("low:{},middle:{},height:{}",low,middle,height);
            if(key < a[middle])
                height = middle - 1;
            else if(key > a[middle])
                low = middle + 1;
            else
                return middle;
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] array = {1,3,4,10,7,8};
        Arrays.sort(array);
        int key = 5;
        int result = rank(key,array);
        System.out.println(result);

        double d = 1.0 / 0.0 ;
        double d2 = -1.0 /0.0;
        System.out.println(d);
        System.out.println(d2);
    }
}
