package com.deng.study.java.thread;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/16 15:12
 */
public class FinalTest {
    static int A = 10;
    final static int B = Short.MAX_VALUE + 1;

}

class UseFinal{
    public void test(){
        System.out.println(FinalTest.A);
        System.out.println(FinalTest.B);
    }
}
