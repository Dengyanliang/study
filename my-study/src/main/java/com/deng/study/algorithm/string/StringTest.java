package com.deng.study.algorithm.string;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @Desc:字符串比较
 * @Auther: dengyanliang
 * @Date: 2021/2/9 10:19
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = "SSSSSSSSSSSSASSSSB";
        String s2 = "SSSSA";
        boolean flag = check(s1,s2);
        System.out.println("flag="+flag);

        /**
         * S的前缀和后缀都是空集，共有元素为0个，所以next[0] = 0
         * SS的前缀是[S]，后缀是[S]，共有元素最大长度为1个，所以next[1]=1
         * SSS的前缀是[S，SS]，后缀是[SS，S]，共有元素最大长度为2个，所以next[2]=2
         * SSSS的前缀是[S，SS，SSS]，后缀是[SSS，SS，S]，共有元素最大长度为3个，所以next[3]=3
         * SSSSB的前缀是[S，SS，SSS，SSSS]，后缀是[SSSB，SSB，SB，B]，共有元素最大长度为0个，所以next[4]=0
         */
        int[] next = kmpNext(s2);
        System.out.println(Arrays.toString(next));

        int index = kmpCheck(s1,s2,next);
        System.out.println("index:"+index);
    }

    /**
     * 最直接最简单的办法，效率不高
     * @param s1
     * @param s2
     * @return
     */
    public static boolean check(String s1,String s2){
        boolean flag = false;
        if(StringUtils.isBlank(s1) || StringUtils.isBlank(s2)){
            System.out.println("存在空串，无法比较");
            return flag;
        }

        int i = 0; // 指向s1的索引变量
        int j = 0; // 指向s2的索引变量

        while(i < s1.length() &&  j < s2.length()){
            if(s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
                flag = true;
            }else{
                i = i - j + 1; // 不相等后，从本次匹配成功的第二个字符开始，依次比较
                j = 0 ;        // 从头开始
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 第二种办法，KMP算法，高效
     * @param s1
     * @param s2
     * @param next
     * @return
     */
    public static int kmpCheck(String s1,String s2,int[] next){
        for(int i = 0, j = 0; i < s1.length(); i++){
            //不相等时，需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)为止
            while(j > 0 && s1.charAt(i) != s2.charAt(j)){
                j = next[j-1];
            }
            if(s1.charAt(i) == s2.charAt(j)){
                j++;
            }
            if(j == s2.length()){
                return i -j +1;
            }
        }
        return -1;
    }
    /**
     * 获取子串的部分匹配表
     * @param dest 子串
     * @return 匹配列表
     */
    public static int[] kmpNext(String dest){
        int[] next = new int[dest.length()];
        next[0] = 0; // 这是kmp算法的规定，第一个字符为0，因为它的前缀和后缀都是空集
        // i是next数组的下标，j是next数组下标对应的值
        for(int i = 1, j = 0; i < dest.length(); i++){

            //不相等时，需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)为止
            while(j > 0 && dest.charAt(i) != dest.charAt(j)){
                System.out.println("j：" + j + "，dest.charAt(i)：" + dest.charAt(i) + "，dest.charAt(j)：" + dest.charAt(j) + "，next[j-1]：" + next[j - 1]);
                j = next[j-1];
            }

            if(dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
