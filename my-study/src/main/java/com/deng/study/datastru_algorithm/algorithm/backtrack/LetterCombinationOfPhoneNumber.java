package com.deng.study.datastru_algorithm.algorithm.backtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Desc: 手机键盘数字字母全排列
 * @Auther: dengyanliang
 * @Date: 2023/5/21 11:11
 */
public class LetterCombinationOfPhoneNumber {

    public static void main(String[] args) {
        LetterCombinationOfPhoneNumber letterCombinationOfPhoneNumber = new LetterCombinationOfPhoneNumber();
        System.out.println(letterCombinationOfPhoneNumber.letterCombinations("23"));
        System.out.println(letterCombinationOfPhoneNumber.letterCombinations(""));
        System.out.println(letterCombinationOfPhoneNumber.letterCombinations("2"));
    }
    

    // 为了方便快速查询，将数字按键和字母的对应关系保存在一个HashMap中
    HashMap<Character,String> numberMap = new HashMap<Character, String>(){
        {
            put('2',"abc");
            put('3',"def");
            put('4',"ghi");
            put('5',"jkl");
            put('6',"mno");
            put('7',"qprs");
            put('8',"tuv");
            put('9',"wxyz");
        }
    };

    /**
     * 回溯法
     * @param digits 输入的数字
     * @return 返回数字对应的字母组合
     */
    private List<String> letterCombinations(String digits){
        // 定义ArrayList保存结果
        List<String> result = new ArrayList<>();

        // 定义一个可行的字母组合
        StringBuffer combination = new StringBuffer();

        backtrack(digits,result,combination,0);

        return result;
    }

    /**
     * 定义递归方法，进行回溯处理
     * @param digits 输入的数字
     * @param result 返回的结果
     * @param combination 可行的字母组合
     * @param i 当前数字的索引位置，默认从头开始，也就是从0开始
     */
    private void backtrack(String digits, List<String> result, StringBuffer combination, int i) {
        // 输入数字长度
        int n = digits.length();

        // 基准情况，如果已经遍历完了所有数字，则得到了一种字母组合
        if (i >= n) {
            result.add(combination.toString());
        } else {
            // 如果没遍历完，处理当前位置的数字，考虑可能的所有字母情况
            // 根据索引获取数字
            char digit = digits.charAt(i);
            // 根据数字从map中获取对应的字符串信息
            String letters = numberMap.get(digit);

            // 得到所有可能的字母
            for (int j = 0; j < letters.length(); j++) {
                // 1、将当前字母添加到组合中
                combination.append(letters.charAt(j));

                // 2、递归调用，继续处理后面的数字
                backtrack(digits,result,combination,i+1);

                // 3、回溯，继续处理下一个可能的字母
                combination.deleteCharAt(i);
            }
        }
    }


}
