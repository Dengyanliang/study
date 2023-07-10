package com.deng.study.datastru_algorithm.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringTest {
    public static void main(String[] args) {
        String s1 = "ABABABDABCAB";
//        String s2 = "AAAAB";    // 不匹配场景
//        String s2 = "ABAB";     // 开头匹配
//        String s2 = "ABDABC";   // 中间匹配
        String s2 = "ABCAB";    // 结尾匹配
        int result = commonCompare(s1, s2);
        System.out.println("commonCompare index：" + result);

        /**
         * 模式串：AAAAB
         * 约定next[0]=-1
         * j=1时，A前缀和后缀都是空集，共有元素为0个，所以next[1] = 0
         * j=2时，AA的前缀是[A]，后缀是[A]，共有元素为1个，所以next[2]=1
         * j=3时，AAA的前缀是[A，AA]，后缀是[AA，A]，共有元素为2个，所以next[3]=2
         * j=4时，AAAA的前缀是[A，AA，AAA]，后缀是[AAA，AA，A]，共有元素3个，所以next[4]=3
         */
        /**
         * 模式串：ABDABC
         * 约定next[0]=-1
         * j=1时，A的前缀和后缀都是空集，共有元素为0个，所以next[1] = 0
         * j=2时，AB的前缀是[A]，后缀是[B]，共有元素为0个，所以next[2]=0
         * j=3时，ABD的前缀是[A，AB]，后缀是[B，BD]，共有元素为0个，所以next[3]=0
         * j=4时，ABDA的前缀是[A，AB，ABD]，后缀是[BDA，DA，A]，共有元素1个，所以next[4]=1
         * j=5时，ABDAB的前缀是[A，AB，ABD，ABDA]，后缀是[BDAB，DAB，AB，B]，共有元素2个，所以next[5]=2
         */
        int[] next = getNext(s2);
        System.out.println("next：" + Arrays.toString(next));

        int index = kmpCheck(s1,s2,next);
        System.out.println("next index:"+index);
//
        int[] nextval = getNextval(s2);
        System.out.println("nextval：" + Arrays.toString(nextval));
//
        int index2 = kmpCheck2(s1,s2,nextval);
        System.out.println("nextval index:"+index2);
    }

    /**
     * 求最大重复子串
     */
    @Test
    public void getMaxStr(){
        String s = "SSSSA";
        int maxi = 0,maxlen = 0, i =0, j=0,len=0;
        while(i<s.length()){ // 从字符串的开头循环
            j=i+1;           // 从i后面的字符循环
            while(j<s.length()){
                if(s.charAt(i) == s.charAt(j)){ // 找一个子串，其起始下标为i，长度为len
                    len = 1;
                    // 从当前i，j位置开始，还有多少个相等的字符
                    for (int k = 1; (i + k) < s.length() && (j + k) < s.length() && s.charAt(i + k) == s.charAt(j + k); k++) {
                        len++;
                    }
                    if(len > maxlen){ // 将较大长度者赋给maxi和maxlen
                        maxi = i;     // 最大长度者的开始位置
                        maxlen = len;
                    }
                    j = j + len; // 已经比对成功了多长的字符
                }else{
                    j++;
                }
            }
            i++; // 继续扫描第i字符之后的字符
        }

        char[] t = new char[maxlen]; // 将最大重复子串赋给t
        for(i=0; i < maxlen; i++){
            t[i] = s.charAt(i + maxi);  // 从maxi位置开始取值
        }
        String result = Arrays.toString(t);
        System.out.println(result);
    }

    /**
     * 普通比较
     * @param s1 原串
     * @param s2 模式串
     * @return
     */
    private static int commonCompare(String s1,String s2){
        if(StringUtils.isBlank(s1) || StringUtils.isBlank(s2)){
            System.out.println("存在空串，不匹配");
            return -1;
        }
        if(s1.length() < s2.length()){
            System.out.println("模式串的长度比原串长度长，不匹配");
            return -1;
        }
        int i = 0 , j = 0;
        while(i < s1.length() && j < s2.length()){
            // 从头开始比较，如果相等，则两个字符串指针均后移
            if(s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
            } else {
                // 一旦发现不相等的情况，则i退回到 i-j+1处，j到0处
                // 为啥是i-j+1? 因为刚开始的时候，i=0，j=0，两个指针一起往后移，一旦发现不匹配，则i需要从第二个位置开始，也就是下标为1的地方，
                // 由于此时i=j，所以是i-j+1;
                i = i - j + 1;
                j = 0;
            }
        }
        if(j >= s2.length()){
            return i - s2.length();
        }else{
            System.out.println("模式串未匹配到完");
            return -1;
        }
    }

    // https://blog.csdn.net/weixin_52622200/article/details/110563434
    private static int[] getNext(String dest){
        int[] next = new int[dest.length()];
        next[0] = -1;
        int k = -1; // 从模式串的开头开始匹配，所以后面要有k=-1，k++，即k=0。k会回退
        int j = 0; // 从模式串的开头开始匹配，在匹配过程中，j会一直递增
        while (j < dest.length() - 1){
//            System.out.println("dest："+dest);
//            System.out.println("  计算0前 k：" + k + "，j：" + j + "，next[j]="+"next[" + j + "]=" + next[j]);
            if(k == -1 || dest.charAt(j) == dest.charAt(k)){
                k++;
                j++;
                next[j] = k; // 说明模式串dest[j]之前有k个字符已成功匹配，下一趟应该从dest[k]开始匹配
//                System.out.println("  计算1后 k：" + k + "，j：" + j + "，next[j]=" + "next[" + j + "]=" + next[j]);
            }else {
//                System.out.println("  计算2前 k：" + k + "，j：" + j + "，k=next[k]=" + "next[" + k + "]=" + next[k]);
                k = next[k]; // 表示从第几个字符开始再次比较
            }
        }
        return next;
    }

    private static int[] getNextval(String dest){
        int[] nextval = new int[dest.length()];
        nextval[0] = -1;
        int k = -1; // 从模式串的开头开始匹配，所以后面要有k=-1，k++，即k=0。k会回退
        int j = 0; // 从模式串的开头开始匹配，在匹配过程中，j会一直递增
        while (j < dest.length() - 1){
//            System.out.println("dest："+dest);
//            System.out.println("  计算0前 k：" + k + "，j：" + j + "，nextval[j]="+"nextval[" + j + "]=" + nextval[j]);
            if(k == -1 || dest.charAt(j) == dest.charAt(k)){
                k++;
                j++;
                if(dest.charAt(j) != dest.charAt(k)){
                    nextval[j] = k;
                }else{
                    nextval[j] = nextval[k];
                }
//                System.out.println("  计算1后 k：" + k + "，j：" + j + "，nextval[j]=" + "nextval[" + j + "]=" + nextval[j]);
            }else {
//                System.out.println("  计算2前 k：" + k + "，j：" + j + "，k=nextval[k]=" + "nextval[" + k + "]=" + nextval[k]);
                k = nextval[k];
            }
        }
        return nextval;
    }


    private static int kmpCheck(String s1,String s2,int[] next){
        if(StringUtils.isBlank(s1) || StringUtils.isBlank(s2)){
            System.out.println("存在空串，不匹配");
            return -1;
        }
        if(s1.length() < s2.length()){
            System.out.println("模式串的长度比原串长度长，不匹配");
            return -1;
        }

        int i = 0, j = 0;
        while(i < s1.length() && j < s2.length()){
            if(j==-1 || s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
            }else{
                j = next[j];
            }
        }
        if(j >= s2.length()){
            return i-s2.length();
        }else{
            return -1;
        }
    }

    /*******************************之前 old code begin******************************************/

    /**
     * 获取子串的部分匹配表
     * @param dest 子串
     * @return 匹配列表
     */
    public static int[] getNext3(String dest){
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

    /**
     * 第二种办法，KMP算法，高效
     * @param s1
     * @param s2
     * @param next
     * @return
     */
    public static int kmpCheck2(String s1,String s2,int[] next){
        for(int i = 0, j = 0; i < s1.length(); i++){
            //不相等时，需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)为止
            while(j > 0 && s1.charAt(i) != s2.charAt(j)){
                j = next[j-1];
            }
            if (j == -1 || s1.charAt(i) == s2.charAt(j)) {
                j++;
            }
            if(j == s2.length()){
                return i -j +1;
            }
        }
        return -1;
    }

    /*********************************之前 old code end******************************************/


    /**
     * 删除字符串中出现次数最少的字符
     */
    @Test
    public void strRemove() {
        String s = "assssa";

        // 统计字符串每个字符出现的次数
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }

        // 统计字符次数最小的值
        int minCount = Integer.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Integer value = entry.getValue();
            if (value < minCount) {
                minCount = value;
            }
        }

        // 把字符次数最小的值对应的字符删除
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            Integer count = map.get(c);
            if (minCount == count) {
                continue;
            } else {
                buffer.append(c);
            }
        }
        String result = buffer.toString();
        System.out.println(result);
    }

    /**
     * 字符串逆序
     */
    @Test
    public void strResverse(){

        String str = "abcd";
        char[] array = new char[str.length()];

        for(int i= 0; i < str.length(); i++){
            char c = str.charAt(i);
            array[array.length-i-1] = c;
        }
        StringBuilder result = new StringBuilder();
        for (char c : array) {
            result.append(c);
        }
        System.out.println(result.toString());
    }
}
