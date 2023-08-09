package com.deng.study.test;


import org.apache.commons.lang.StringUtils;

public class Test {

    public static void main(String[] args) {
        String s = "123";

        int anInt = convertToInt(s);
        System.out.println(anInt);
    }

    private static int convertToInt(String str) {
        if(StringUtils.isBlank(str)){
            return 0;
        }
        // 如果不是数字，直接报错
        // 如果超限也要报错

        int result = 0;
        int n = str.length()-1;
        while(n >= 0){
            int c = str.charAt(n) - '0';
            if(n == str.length()-1){
                result = c;
            }else{
                result += c * 10;
            }
            n--;
        }
        return result;
    }
}

