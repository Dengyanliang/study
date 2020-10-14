package com.deng.study.algorithm.basic;

import com.deng.study.source.StdOut;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/12 19:30
 */
@Slf4j
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
        System.out.println();
    }

    private static void test2(){
        double t = 9.0;
        while (Math.abs(t-9.0/t) > 0.001)
            t = (9.0/t+t)/2.0;
        log.info("t:{}",t);

        int sum = 0;
        for(int i = 1; i < 7; i++) // 1,2,3,4
            for(int j = 0; j < i; j++){ // n*(n-1)/2
                sum++;
                log.info("i:{},j:{}",i,j);
            }

        log.info("sum:{}",sum);

        int sum2 = 0;
        for(int i =1; i < 5; i *= 2)  // 1,2,4   2^(n-1) < 10  n = 4
            for(int j = 0; j < 3; j++){ // 1,2,3,4,5
                sum2++; // 3 * 5 = 15
                log.info("i:{},j:{}",i,j);
            }
        log.info("sum2:{}",sum2);

    }


    public static void main(String[] args) {
        System.out.println('b');
        System.out.println('b'+'c'); // 为啥是 197  ASCII中 b=98 c=99
        System.out.println(('b'+4)); // 102 char类型在计算的时候，会先转化为int类型
        System.out.println((char)('b'+4)); // f 这里是强制转换

        testArray();

        System.out.println((1+2.23)/2); // 按照最高类型计算  1.615
        System.out.println(1+2+3+4.0);
        System.out.println(4.1>4);
        System.out.println(1+2+"3");

        test2();
    }
}
