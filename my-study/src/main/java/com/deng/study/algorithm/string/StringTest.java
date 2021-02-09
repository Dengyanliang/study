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
        String s1 = "ABCDABDEF";
        String s2 = "ABD";
        boolean flag = check(s1,s2);
        System.out.println("flag="+flag);

//        s2 = "AAAB";
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
            System.out.println("charAt(i)="+s1.charAt(i) + ",charAt(j)=" + s2.charAt(j));
            if(s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
                flag = true;
            }else{
                i = i - j + 1; // 不相等后，从本次匹配成功的第二个字符开始，依次比较
                System.out.println("不相等");
                j = 0 ;        // 从头开始
                flag = false;
            }
            System.out.println("s1.length()=" +s1.length()  + ",s2.length()=" +s2.length()  +",i="+i + ",j=" + j);
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
        boolean flag = false;
        for(int i = 0, j = 0; i < s1.length(); i++){
            //不相等时，需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)为止
            while(j > 0 && s1.charAt(i) != s2.charAt(j)){
                j = next[j-1];
            }
            if(s1.charAt(i) == s2.charAt(j)){
                j++;
            }
            if(j == s2.length()){
                flag = true;
//                break;
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
        next[0] = 0; // 这是kmp算法的规定，第一个字符为0，因为没有人和它进行匹配
        for(int i = 1, j = 0; i < dest.length(); i++){

            //不相等时，需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)为止
            while(j > 0 && dest.charAt(i) != dest.charAt(j)){
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
