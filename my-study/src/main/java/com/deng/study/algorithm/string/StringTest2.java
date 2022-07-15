package com.deng.study.algorithm.string;

import org.apache.commons.lang3.StringUtils;

public class StringTest2 {
    public static void main(String[] args) {
        String s1 = "BADXCFEXDFD";
        String s2 = "ADX";
        boolean result = compare(s1, s2);
        System.out.println("普通对比的结果是：" + result);

    }

    /**
     * 普通比较
     * @param s1 原串
     * @param s2 模式串
     * @return
     */
    private static boolean compare(String s1,String s2){
        if(StringUtils.isBlank(s1) || StringUtils.isBlank(s2)){
            System.out.println("存在空串，不匹配");
            return false;
        }
        if(s1.length() < s2.length()){
            System.out.println("模式串的长度比原串长度长，不匹配");
            return false;
        }
        int i = 0 , j = 0;
        boolean flag = false;
        while(i < s1.length() && j < s2.length()){
            // 从头开始比较，如果相等，则两个字符串指针均后移
            if(s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
                flag = true;
            } else {
                // 一旦发现不相等的情况，则i退回到 i-j+1处，j到0处
                // 为啥是i-j+1? 因为刚开始的时候，i=0，j=0，两个指针一起往后移，一旦发现不匹配，则i需要从第二个位置开始，也就是下标为1的地方，
                // 由于此时i=j，所以是j-j+1
                i = i-j+1;
                j = 0;
                flag = false;
            }
        }
        if(j < s2.length()){
            System.out.println("模式串未匹配到完");
            return false;
        }
        return flag;
    }
}
