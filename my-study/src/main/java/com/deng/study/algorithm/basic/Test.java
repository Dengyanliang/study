package com.deng.study.algorithm.basic;

import com.deng.study.source.StdOut;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/12 19:30
 */
public class Test {


    public static void testArray(){
        int[] a = new int[10];
        for(int i = 0; i < 10; i++){
            a[i] =  9 - i;
            System.out.print(a[i]+" "); // 9 8 7 6 5 4 3 2 1 0
        }
        System.out.println();
        for(int i = 0; i < 10; i++){
            a[i] =  a[a[i]];
            System.out.print(a[i]+" "); // 0 1 2 3 4 4 3 2 1 0
        }
        System.out.println();
        for(int i = 0; i < 10; i++){
            System.out.print(i+ " ");  // 0 1 2 3 4 5 6 7 8 9
        }
    }


    public static void main(String[] args) {
        System.out.println('b');
        System.out.println('b'+'c'); // 为啥是 197  ASCII中 b=98 c=99
        System.out.println(('b'+4)); // 102 char类型在计算的时候，会先转化为int类型
        System.out.println((char)('b'+4)); // f 这里是强制转换

        testArray();
    }
}
