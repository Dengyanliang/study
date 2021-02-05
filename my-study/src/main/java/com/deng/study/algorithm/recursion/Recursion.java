package com.deng.study.algorithm.recursion;

import com.deng.study.source.StdOut;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:递归
 * @Auther: dengyanliang
 * @Date: 2020/10/15 20:16
 */
@Slf4j
public class Recursion {

    public static String exR1(int n){
        if(n <= 0)
            return "";
        String result = exR1(n-3);// 3 result = "3"+3
        result += n;
        result += exR1(n-2); // 3-2    result = 1-3  ""+
        result += n;
        return result;
//        return exR1(n-3)+n+exR1(n-2)+n;
    }

    private static int mystery(int a, int b){
        if(b==0)
            return 0;
        if(b % 2 ==0){
            int result = mystery(a+a,b/2);
            return result;
        }
        int result = mystery(a+a,b/2);
        return result + a;
    }

    public static void main(String[] args) {
        log.info("exR1(3)：{}",exR1(3));

        log.info("mystery(2,25):{}",mystery(2,25));
        StdOut.println();
        log.info("mystery(3,11):{}",mystery(3,11));
    }

}
