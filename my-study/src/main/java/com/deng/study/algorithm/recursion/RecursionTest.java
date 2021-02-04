package com.deng.study.algorithm.recursion;

import com.deng.study.source.StdOut;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:递归
 * @Auther: dengyanliang
 * @Date: 2020/10/15 20:16
 */
@Slf4j
public class RecursionTest {

    public static String exR1(int n){
        log.info("exR1 n:{}",n);
        if(n <= 0)
            return "";
        log.info("------");
        String result = exR1(n-3);
        result += n;
        result += exR1(n-2);
        result += n;
        log.info("exR1 result:{}",result);
        return result;
//        return exR1(n-3)+n+exR1(n-2)+n;
    }

    private static int mystery(int a, int b){
        log.info("a:{},b:{}",a,b);
        if(b==0)
            return 0;
        if(b % 2 ==0){
            log.info("a+a:{},b/2:{}",(a+a),b/2);
            int result = mystery(a+a,b/2);
            log.info("result:{}",result);
            return result;
        }
        log.info("=======a+a:{},b/2:{}",(a+a),b/2);
        int result = mystery(a+a,b/2);
        log.info("=======result:{}",result);
        return result + a;
    }

    public static void main(String[] args) {
        log.info("exR1(3)：{}",exR1(3));

        log.info("mystery(2,25):{}",mystery(2,25));
        StdOut.println();
        log.info("mystery(3,11):{}",mystery(3,11));
    }

}
