package com.deng.study.datastru_algorithm.string;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Desc: 字符串排序
 * @Auther: dengyanliang
 * @Date: 2023/1/31 16:52
 */
public class StringSort {

    public static void main(String[] args) {
//        String str = "By?e";
//        String str = "Type";
        String str = "BabA";
        str = sort(str);
        System.out.println(str);
    }

    public static String sort(String str) {
        char[] chars = str.toCharArray();
        // 1、先把字符串中的字母过滤出来，放到一个集合中
        List<Character> characterList = new ArrayList<>();
        for (char c : chars) {
            if(Character.isLetter(c)){
                characterList.add(c);
            }
        }

        // 2、对characterList 按ascii码 进行排序
        characterList.sort(Comparator.comparingInt(Character::toLowerCase));

        // 保存结果
        StringBuilder result = new StringBuilder();

        // 3、遍历原始字符串
        for (int i = 0, j = 0; i < chars.length; i++) {
            if(Character.isLetter(chars[i])){ // 如果是字符，则从characterList获取，因为已经有序
                result.append(characterList.get(j));
                j++;
            }else{                            // 如果不是字符，则直接添加
                result.append(chars[i]);
            }
        }
        return result.toString();
    }
}
