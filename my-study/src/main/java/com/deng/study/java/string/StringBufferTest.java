package com.deng.study.java.string;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/10/12 19:02
 */
public class StringBufferTest {

    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer("A");
        StringBuffer s2 = new StringBuffer("B");
        com(s1,s2);
        System.out.println(s1); // AB
        System.out.println(s2); // B
    }

    public static void com(StringBuffer s1,StringBuffer s2){
        s1.append(s2);
        s2 = s1;
    }
}
