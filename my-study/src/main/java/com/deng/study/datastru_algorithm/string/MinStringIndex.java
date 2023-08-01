package com.deng.study.datastru_algorithm.string;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Desc: 求最后一个子串的起始索引值
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/14 16:51
 */
public class MinStringIndex {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String source = "abc";
//        String target = "abcaybzc";
        String target = "abca";

        Map<Character,Integer> sourceMap = new HashMap<>();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            sourceMap.put(c,sourceMap.getOrDefault(c,0)+1);
        }

        int index = -1;
        for (int i = 0; i < target.length(); i++) {
            for (int j = i; j < target.length(); j++) {
                Map<Character,Integer> targetMap = new HashMap<>();
                for (int k = j; k < target.length(); k++) {
                    char c = target.charAt(k);
                    targetMap.put(c,targetMap.getOrDefault(c,0)+1);
                }

                boolean flag = checkExist(sourceMap, targetMap);
                if(flag){

                }
            }
        }
        System.out.println(index);
    }

    private static boolean checkExist(Map<Character, Integer> sourceMap, Map<Character, Integer> targetMap) {
        for (Map.Entry<Character, Integer> entry : sourceMap.entrySet()) {
            Integer sourceCount = entry.getValue();
            if(targetMap.containsKey(entry.getKey())){
                Integer targetCount = targetMap.get(entry.getKey());
                if(targetCount < sourceCount){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
}
