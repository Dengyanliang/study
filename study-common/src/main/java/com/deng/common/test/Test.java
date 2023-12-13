package com.deng.common.test;


import org.apache.commons.lang3.StringUtils;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/29 14:57
 */
public class Test {

    /**
     * 要求出两个字符串相加的结果，两个字符确保数字，但是非常长，长到无法convert to number，求出两个字符串相加结果
     *
     * String calc(String number1, String number2)
     *
     * @param args
     */
    public static void main(String[] args) {
        // 1、遍历两个字符串，从最后开始取出每个位置上的值进行累加

        // 2、定义一个进制数，用于每次累加的结果

        String result = new Test().calc("22233442121", "2323422223");
        System.out.println(result);

    }

    private String calc(String number1, String number2){
        // 校验
        if(StringUtils.isBlank(number1) && StringUtils.isBlank(number2)){
            return "";
        }

        if(StringUtils.isBlank(number1) && StringUtils.isNotBlank(number2)){
            return number2;
        }

        if(StringUtils.isNotBlank(number1) && StringUtils.isBlank(number2)){
            return number1;
        }

        int l1 = number1.length();
        int l2 = number2.length();
        StringBuilder result = new StringBuilder();

        while(l1 > 0 && l2 > 0){
            int c1 = number1.charAt(l1 - 1) - '0';
            int c2 = number2.charAt(l2 - 1) - '0';
            int d = (c1 + c2)/10; // 进制
            int value = (c1 + c2) % 10 + d;

            result.append(value);

            l1--;
            l2--;
            if(l1 == 0 || l2 == 0){
                break;
            }
        }

        // number1遍历完而number2没有遍历完
        if(l1 == 0 && l2 > 0){
            for (int i = l2-1; i >=0; i--) {
                int c2 = number2.charAt(i) - '0';
                result.append(c2);
            }
        }

        // number2遍历完而number1没有遍历完
        if(l1 > 0 && l2 == 0){
            for (int i = l1-1; i >=0; i--) {
                int c1 = number1.charAt(i) - '0';
                result.append(c1);
            }
        }

        return result.reverse().toString();
    }
}

