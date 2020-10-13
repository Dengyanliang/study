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


    /**
     * 将10进制转换为2进制，转化为其他进制也一样
     * @param n
     * @return
     */
    private static String tenToTwo(int n){
        // 先除以2，得到余数
        String s = "";
        for(int i = n; i > 0; i /= 2){
            log.info("i:{},i%2:{}", i, i % 2);
            s =( i % 2) + s;
        }

//        String s = Integer.toBinaryString(n);  // 转化为2进制

        return s;
    }

    /**
     * 将10进制转换为2进制，转化为其他进制也一样
     * @param n
     * @return
     */
    private static String tenToEight(int n){
        // 先除以8，得到余数
//        String s = "";
//        for(int i = n; i > 0; i /= 16){
//            log.info("i:{},i%8:{}", i, i % 16);
//            s =( i % 16) + s;
//        }
//        log.info("s:{}",s);

//        String s = Integer.toHexString(n);  // 转化为16进制
        String s = Integer.toOctalString(n);  // 转化为8进制

        return s;
    }


    private static void twoToTen(int n){
        int result = 0;
        int t = 0;         // 记录位数
        int remainder = 0; // 记录余数
        for(int i = n; i > 0 ; i /= 10){
            remainder = i % 10;
            log.info("i:{},remainder:{}",i,remainder);
            result += remainder * Math.pow(2,t); // 2的t次方
            t++;
        }
        log.info("result:{}",result);

    }

    public static void main(String[] args) {
        boolean prime = isPrime(9);
        log.info("prime:{}",prime);

        double hypotenuse = hypotenuse(5,4);
        log.info("hypotenuse:{}",hypotenuse);

        String tenToTwo = tenToTwo(15);
        log.info("tenToTwo:{}",tenToTwo);

        String tenToEight = tenToEight(15);
        log.info("tenToEight:{}",tenToEight);

        twoToTen(011);
    }
}
