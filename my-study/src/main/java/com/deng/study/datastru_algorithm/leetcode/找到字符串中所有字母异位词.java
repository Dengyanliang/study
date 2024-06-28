package com.deng.study.datastru_algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 找到字符串中所有字母异位词 {
    public static void main(String[] args) {

        String s = "cbaebabacd", p = "abc";

        System.out.println(get(s, p));
    }

    private static List<Integer> get(String s, String p){
        int sLen = s.length(),pLen = p.length();
        if(sLen < pLen){
            return new ArrayList<>();
        }

        List<Integer> resultList = new ArrayList<>();
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        // 第一步，计算sCount，pCount的值
        for (int i = 0; i < pLen; i++) {
            sCount[s.charAt(i)-'a']++;
            pCount[p.charAt(i)-'a']++;
        }
        if(Arrays.equals(sCount,pCount)){
            resultList.add(0);
        }
        for (int i = 0; i < sLen-pLen; i++) {
            // 先对计算好的sCount数组的索引下标减1，再对sCount数组的索引下标加1，目的是消除第一步中已经判断好的值
            sCount[s.charAt(i)-'a']--;      //
            sCount[s.charAt(i+pLen)-'a']++; //

            if(Arrays.equals(sCount,pCount)){
                resultList.add(i+1);
            }
        }
        return resultList;
    }
}
