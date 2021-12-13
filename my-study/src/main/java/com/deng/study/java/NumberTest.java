package com.deng.study.java;



import org.junit.Test;

import java.math.BigDecimal;

public class NumberTest {

    @Test
    public void test(){
        for (int i = 0; i < 5; i++) {
            BigDecimal sum = new BigDecimal(0);
            for (int j = 0; j < 3; j++) {
                sum = sum.add(new BigDecimal(j));
            }
            System.out.println(sum);
        }
    }

    @Test
    public void test1(){
        int num = 0;
        int numNeeded = 10;
        int s1 = 2;
        num = numNeeded;
        if(num > s1){
            num = s1;
        }
//        num = Math.min(numNeeded, s1);
        System.out.println(num);
    }
}
