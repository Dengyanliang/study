package com.deng.study.algorithm.string;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringTest2 {
    public static void main(String[] args) {
        String s1 = "SSSSSSSSSSSSASSSSB";
        String s2 = "SSSSB";
        boolean result = compare(s1, s2);
        System.out.println("普通对比的结果是：" + result);

        /**
         * 模式串：ABDABC
         *
         * A的前缀和后缀都是空集，共有元素为0个，所以next[0] = 0
         * AB的前缀是[A]，后缀是[B]，共有元素为0个，所以next[1]=1
         * ABD的前缀是[A，AB]，后缀是[B，BD]，共有元素为0个，所以next[2]=1
         * ABDA的前缀是[A，AB，ABD]，后缀是[BDA，DA，A]，共有元素1个，所以next[3]=2
         * ABDAB的前缀是[A，AB，ABD，ABDA]，后缀是[BDAB，DAB，AB，B]，共有元素2个，所以next[4]=3
         * ABDABC的前缀是[A，AB，ABD，ABDA，ABDAB]，后缀是[BDABC，DABC，ABC，BC，C]，共有前缀0个，所以next[5]=1
         */
        int[] next = getNext(s2);
        System.out.println(Arrays.toString(next));

        int[] next2 = getNext2(s2);
        System.out.println(Arrays.toString(next2));

//        int[] next3 = getNext3(s2);
//        System.out.println(Arrays.toString(next3));

        int[] next4 = getNext4(s2);
        System.out.println(Arrays.toString(next4));

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
                // 由于此时i=j，所以是i-j+1;
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

    // https://blog.csdn.net/weixin_52622200/article/details/110563434
    private static int[] getNext(String dest){
        int next[] = new int[dest.length()];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < dest.length() - 1){
            if(k == -1 || dest.charAt(j) == dest.charAt(k)){
                next[++j] = ++k;
            }else {
                k = next[k];
            }
        }
        return next;
    }



    private static int[] getNext2(String dest){
        int next[] = new int[dest.length()];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < dest.length() - 1){
            if(k == -1 || dest.charAt(j) == dest.charAt(k)){
               if(dest.charAt(++j) == dest.charAt(++k)){
                   next[j] = next[k];
               }else{
                   next[j] = k;
               }
            }else {
                k = next[k];
            }
        }
        return next;
    }

    private static int[] getNext3(String dest){
        int[] next = new int[dest.length()];
        next[0] = 0;
        int j = 1, k = 0;
        while(k < dest.length() - 1){
            if(k == 0 || dest.charAt(j) == dest.charAt(k)){
                next[j] = k + 1;
                ++k;
                ++j;
            }else{
                k = next[k];
            }
        }
        return next;
    }

    public static int[] getNext4(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return next;
    }
}
