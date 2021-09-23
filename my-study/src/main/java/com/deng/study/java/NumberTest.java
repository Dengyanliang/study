package com.deng.study.java;


import org.junit.jupiter.api.Test;

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
}
