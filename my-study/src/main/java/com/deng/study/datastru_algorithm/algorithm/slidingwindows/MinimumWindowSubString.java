package com.deng.study.datastru_algorithm.algorithm.slidingwindows;

import java.util.HashMap;

/**
 * @Desc: 最小子串问题
 * @Auther: dengyanliang
 * @Date: 2023/6/19 11:58
 */
public class MinimumWindowSubString {
    public static void main(String[] args) {
        MinimumWindowSubString subString = new MinimumWindowSubString();
        System.out.println(subString.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(subString.minWindow1("ABBCA", "ABC"));
        System.out.println(subString.minWindow("a", "a"));
        System.out.println(subString.minWindow("a", "aa"));
    }

    /**
     * 1、暴力法
     *  时间复杂度：O(ls^3)
     *  空间复杂度：O(1)
     *
     * @param s 原始字符串
     * @param t 模式串
     * @return 最小覆盖子串
     */
    public String minWindow1(String s,String t){
        // 定义一个最小子串，用于返回结果
        String minSubString = "";

        // 统计模式串t中每个字符出现的频次
        HashMap<Character,Integer> tCharFrequency = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer count = tCharFrequency.getOrDefault(c, 0);
            tCharFrequency.put(c,count+1);
        }


        // 遍历原始串s的所有子串（以lp为起始位置，以rp为结束位置，不包含lp），并统计每个字符出现的频次
        for (int lp = 0; lp < s.length(); lp++) {
            for (int rp = lp + t.length(); rp <= s.length(); rp++) {
                HashMap<Character, Integer> subStrCharFrequency = new HashMap<>();
                for (int k = lp; k < rp; k++) {
                    char c = s.charAt(k);
                    Integer count = subStrCharFrequency.getOrDefault(c, 0);
                    subStrCharFrequency.put(c, count + 1);
                }

                // 对比t中频次和s子串中频次，如果满足要求（），就是覆盖子串；
                if (check(tCharFrequency, subStrCharFrequency) &&
                        (minSubString.equals("") || rp - lp < minSubString.length())) {
                    minSubString = s.substring(lp, rp);
                }
            }
        }

        return minSubString;
    }

    /**
     * 2、滑动窗口
     *  时间复杂度：O(ls^2)
     *  空间复杂度：O(1)
     * @param s 原始串
     * @param t 模式串
     * @return
     */
    public String minWindow2(String s,String t) {
        // 定义一个最小子串，用于返回结果
        String minSubString = "";

        // 统计模式串t中每个字符出现的频次
        HashMap<Character, Integer> tCharFrequency = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer count = tCharFrequency.getOrDefault(c, 0);
            tCharFrequency.put(c, count + 1);
        }

        // 设置双指针，用来表示窗口的起始位置和结束位置，左闭右开
        int lp = 0, rp = t.length();
        while(rp <= s.length()){
            // 遍历原始串s的所有子串（以lp为起始位置，以rp为结束位置，不包含rp），并统计每个字符出现的频次
            HashMap<Character, Integer> subStrCharFrequency = new HashMap<>();
            for (int k = lp; k < rp; k++) {
                char c = s.charAt(k);
                Integer count = subStrCharFrequency.getOrDefault(c, 0);
                subStrCharFrequency.put(c, count + 1);
            }
            // 对于t中频次和s子串中频次，如果满足要求（t中频次<=s子串中频次），就是覆盖子串
            if (check(tCharFrequency, subStrCharFrequency)){
                // 针对覆盖子串，找出最优解
                if(minSubString.equals("") || rp - lp < minSubString.length()){
                    minSubString = s.substring(lp,rp);
                }
                // 如果找到了覆盖子串（可行解），就移动左指针，寻找局部最优解
                lp++;
            }else{
                // 如果没有找到覆盖子串，就移动右指针，继续寻找可行解
                rp++;
            }
        }
        return minSubString;
    }

    /**
     * 3、滑动窗口优化
     *  时间复杂度：O(ls)+ O(lt) 当lt比较小时，就是O(ls)
     *  空间复杂度：O(1)
     * @param s 原始串
     * @param t 模式串
     * @return
     */
    public String minWindow3(String s,String t) {
        // 定义一个最小子串，用于返回结果
        String minSubString = "";

        // 统计模式串t中每个字符出现的频次
        HashMap<Character, Integer> tCharFrequency = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer count = tCharFrequency.getOrDefault(c, 0);
            tCharFrequency.put(c, count + 1);
        }

        // 统计子串中每个字符出现的频次
        HashMap<Character, Integer> subStrCharFrequency = new HashMap<>();

        // 设置双指针，用来表示窗口的起始和结束位置，左闭右开
        int lp = 0,rp = 1;
        while (rp <= s.length()){
            // 判断当前新增字符，如果在模式串t中，那么就在子串的HashMap中频次加1
            char newChar = s.charAt(rp - 1);
            if(tCharFrequency.containsKey(newChar)){
                Integer count = subStrCharFrequency.getOrDefault(newChar, 0);
                subStrCharFrequency.put(newChar, count + 1);
            }

            // 对于t中频次和s子串中频次，如果满足要求（t中频次<=s子串中频次），就是覆盖子串
            while (lp < rp && check(tCharFrequency, subStrCharFrequency)){
                // 针对覆盖子串，找出最优解
                if(minSubString.equals("") || rp - lp < minSubString.length()){
                    minSubString = s.substring(lp,rp);
                }

                // 删除的字符如果出现在模式串t中，那么就在子串的HashMap中频次减1
                char removeChar = s.charAt(lp);
                if(tCharFrequency.containsKey(removeChar)){
                    Integer count = subStrCharFrequency.getOrDefault(removeChar, 0);
                    subStrCharFrequency.put(removeChar, count - 1);
                }

                // 如果找到了覆盖子串（可行解），就移动左指针，寻找局部最优解
                lp++;
            }

            // 如果没有找到覆盖子串，就移动右指针，继续寻找可行解
            rp++;
        }
        return minSubString;
    }


    /**
     * 4、使用汉明编码优化
     *  时间复杂度：O(ls)+ O(lt) 当lt比较小时，就是O(ls)
     *  空间复杂度：O(1)
     * @param s 原始串
     * @param t 模式串
     * @return
     */
    public String minWindow(String s,String t) {
        // 定义一个最小子串，用于返回结果
        String minSubString = "";

        // 统计模式串t中每个字符出现的频次
        HashMap<Character, Integer> tCharFrequency = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            Integer count = tCharFrequency.getOrDefault(c, 0);
            tCharFrequency.put(c, count + 1);
        }

        // 统计子串中每个字符出现的频次
        HashMap<Character, Integer> subStrCharFrequency = new HashMap<>();

        // 借鉴汉明距离，代表子串中字母对于覆盖t的贡献度
        int charCount = 0;

        // 设置双指针，用来表示窗口的起始和结束位置，左闭右开
        int lp = 0,rp = 1;
        while (rp <= s.length()){
            // 判断当前新增字符，如果在模式串t中，那么就在子串的HashMap中频次加1
            char newChar = s.charAt(rp - 1);
            if(tCharFrequency.containsKey(newChar)){
                Integer count = subStrCharFrequency.getOrDefault(newChar, 0);
                subStrCharFrequency.put(newChar, count + 1);

                // 如果当前字母在子串中的频次不够t中的频次，说明有贡献
                if(subStrCharFrequency.get(newChar) <= tCharFrequency.get(newChar)){
                    charCount++;
                }
            }

            // keypoint 这里去掉了 两个HashMap中的比较与判断
            while (lp < rp && charCount == t.length()){
                // 针对覆盖子串，找出最优解
                if(minSubString.equals("") || rp - lp < minSubString.length()){
                    minSubString = s.substring(lp,rp);
                }

                // 删除的字符如果出现在t中，频次减1
                char removeChar = s.charAt(lp);
                if(tCharFrequency.containsKey(removeChar)){
                    Integer count = subStrCharFrequency.getOrDefault(removeChar, 0);
                    subStrCharFrequency.put(removeChar, count - 1);

                    // 判断删除的字母在子串中出现的频次原本已经够了，现在不够的话，说明删除的字母有贡献
                    if(subStrCharFrequency.get(removeChar) < tCharFrequency.get(removeChar)){
                        charCount--;
                    }
                }

                // 如果找到了覆盖子串（可行解），就移动左指针，寻找局部最优解
                lp++;
            }

            // 如果没有找到覆盖子串，就移动右指针，继续寻找可行解
            rp++;
        }
        return minSubString;
    }



    /**
     * 定义一个方法，用来检查当前子字符串是否满足覆盖t的要求
     *
     * @param tCharFrequency  模式串t对应的HashMap
     * @param subStrCharFrequency 字符串s的子串对应的HashMap
     * @return
     */
    private boolean check(HashMap<Character,Integer> tCharFrequency,HashMap<Character,Integer> subStrCharFrequency){
        for (Character c : tCharFrequency.keySet()) {
            if (subStrCharFrequency.getOrDefault(c, 0) < tCharFrequency.get(c)) {
                return false;
            }
        }
        return true;
    }
}
