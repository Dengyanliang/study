package com.deng.study.algorithm.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/9/26 20:04
 */
@Slf4j
public class NumberTest {

    /**
     * 判断一个数是否为素数，素数是值大于1的自然数中，除了1和它本身之外没有别的因数的自然数
     * @param n
     * @return
     */
    private static boolean isPrime(int n){
        if(n <= 3)
            return n > 1;
        for(int i = 2; i * i <= n; i++){    // 比从2开始比较到N高效
            if(n % i ==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算平方根
     * @param a
     * @param b
     * @return
     */
    private static double hypotenuse(double a,double b){
        return Math.sqrt(a * a + b * b);
    }



    public static void main(String[] args) {
        boolean prime = isPrime(9);
        log.info("prime:{}",prime);

        double hypotenuse = hypotenuse(5,4);
        log.info("hypotenuse:{}",hypotenuse);
    }
}
