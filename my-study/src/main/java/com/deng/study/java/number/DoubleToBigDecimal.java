package com.deng.study.java.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DoubleToBigDecimal {

    public static void main(String[] args) {
        double s = 2.3d;

        MathContext mc = new MathContext(32, RoundingMode.HALF_DOWN);
        BigDecimal bigDecimal = new BigDecimal(s); // 2.2999999999999998223643160599750

        System.out.println(bigDecimal);

        double v = bigDecimal.doubleValue();
        System.out.println(v);

    }
}
