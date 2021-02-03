package com.deng.study.algorithm.linear;

import java.util.Stack;

/**
 * @Desc:使用栈来实现表达式 TODO
 * @Auther: dengyanliang
 * @Date: 2021/2/3 14:58
 */
public class MyStack2 {

    // https://leetcode-cn.com/problems/calculator-lcci/solution/java-si-lu-qing-xi-jian-ji-dai-ma-by-acw_weian/
    public static void main(String[] args) {
        String s = "751+2*3-20";
        char[] cs = s.trim().toCharArray();
        Stack<Integer> st = new Stack();
        int ans = 0, i = 0;
        while(i < cs.length) {
            if (cs[i] == ' ') {
                i++;
                continue;
            }
            char tmp = cs[i];
            if (tmp == '*' || tmp == '/' || tmp == '+' || tmp == '-') {
                i++;
                while (i < cs.length && cs[i] == ' ')
                    i++;
            }
            int num = 0;
            while(i < cs.length && Character.isDigit(cs[i])){
                num = num * 10 + cs[i] - 48;
                System.out.println("num:"+num);
                i++;
            }

            while (i < cs.length && Character.isDigit(cs[i])) { // 是数字
                num = num * 10 + cs[i] - '0';  // '0'的asicii码为48
                System.out.println("num:"+num);
                i++;
            }
        }
    }

}
