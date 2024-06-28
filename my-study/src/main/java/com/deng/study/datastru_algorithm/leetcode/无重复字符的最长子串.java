package com.deng.study.datastru_algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 无重复字符的最长子串 {
    public static void main(String[] args) {
        String s = "abcabcbb";

        int left = 0,right = 0,max= 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            if(!set.contains(s.charAt(right))){
                set.add(s.charAt(right));
                right++;
            }else{
                set.remove(s.charAt(left));
                left++;
            }
            max = Math.max(max,set.size());
        }
        System.out.println(max + "," + set);
    }
}
