package com.deng.study.datastru_algorithm.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:求最大公约数
 * @Auther: dengyanliang
 * @Date: 2020/9/26 16:22
 */
@Slf4j
public class CommonDivisor {

    // 辗转相除法
    private static int method1(int a, int b){
        int c = a % b;
        log.info("a = {},b = {},{} % {} = {}",a,b,a,b,c);
        if(c == 0){
            return b;
        }
        a = b;
        b = c;
        return method1(a,b);
    }

    // 辗转相减法
    private static int method2(int a, int b){
        int c = 0;
        while(true){
            if(a > b){
                c = a - b;
                log.info("a = {},b = {},{} - {} = {}",a,b,a,b,c);
                a = a - b;
            } else{
                c = b - a;
                log.info("a = {},b = {},{} - {} = {}",a,b,b,a,c);
                b = b - a;
            }
            if(a == b){
                log.info("a = {},b = {}",a,b);
                break;
            }
        }
        return a;
    }

    // 穷举法
    private static int method3(int a, int b){
        int temp = 0;
        for(temp = a; ;temp--){
            log.info("a = {},b = {};{} % {} = {},{} % {} = {}", a, b, a, temp, a % temp, b, temp, b % temp);
            if(a%temp ==0 && b%temp ==0){
                break;
            }
        }
        return temp;
    }


    public static void main(String[] args) {
        int result = 0;
        int a = 7, b = 14;
        result = method3(a,b);
        log.info("result:{}",result);
    }
}
