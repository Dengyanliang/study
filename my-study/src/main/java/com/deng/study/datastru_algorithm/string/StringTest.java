package com.deng.study.datastru_algorithm.string;

import com.deng.study.domain.UnionFindSet;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            map.put(c, map.getOrDefault(c, 0) + 1);
//            if (!map.containsKey(c)) {
//                map.put(c, 1);
//            } else {
//                map.put(c, map.get(c) + 1);
//            }
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


    @Test
    public void 火星文计算(){
        String str = "7#6$5#12";
        String reg = "(\\d+)\\$(\\d+)";
        Pattern p = Pattern.compile(reg);
        while (true){
            Matcher m = p.matcher(str);
            if(!m.find())
                break;
            String subStr = m.group(0);
            System.out.println("subStr:"+subStr);

            String group1 = m.group(1);
            System.out.println("group1:"+group1);
            int x = Integer.parseInt(group1);

            String group2 = m.group(2);
            System.out.println("group2:"+group2);
            int y = Integer.parseInt(group2);

            str = str.replace(subStr, 3 * x + y + 2 + "");
            System.out.println("str:"+str);
        }

        Integer result = Arrays.stream(str.split("#")).
                map(Integer::parseInt).reduce((x, y) -> 2 * x + 3 * y + 4).orElse(0);
        System.out.println("result:"+result);
    }

    @Test
    public void 字符统计及重排(){
        String str = "xxyyXXZZ";
//        String str = "abababb";
        HashMap<Character, Integer> letter = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            letter.put(c,letter.getOrDefault(c,0)+1);
        }
        StringBuilder sb = new StringBuilder();
        letter.entrySet().stream().sorted(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                // 如果value值不一样，按value值从大到小排序
                if(o1.getValue() - o2.getValue() != 0){
                    return o2.getValue()-o1.getValue();
                }else{ // value值一样
//                    // 如果都是同类型的，比如都是大小或者都是小写，则按照ascii编码升序
                    if((isLower(o1.getKey()) && isLower(o2.getKey())) ||
                            (isUpper(o1.getKey()) && isUpper(o2.getKey()))){
                        System.out.println("2222222");
                        return o1.getKey()- o2.getKey();
                    }else{
                        // 如果不是同类型的，则小写字母在大写字母前面
                        if(isUpper(o1.getKey())){
                            System.out.println("3333333");
                            return 1;
                        } else{
                            System.out.println("444444");
                            return -1;
                        }
                    }
                }
            }
        }).
        forEach(entry->sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";"));

        String result = sb.toString();
        System.out.println(result);
    }

    private boolean isLower(char letter){
        return letter >= 'a' && letter <= 'z';
    }

    private boolean isUpper(char letter){
        return letter >= 'A' && letter <= 'Z';
    }

    /**
     * 此题并不难，需要细心的看题目
     */
    @Test
    public void 判断一组不等式是否满足约束并输出最大值(){
        String str = "2.3,3,5.6,7,6;11,3,8.6,25,1;0.3,9,5.3,66,7.8;1,3,2,7,5;340,670,80.6;<=,<=,<=";
        String[][] array = Arrays.stream(str.split(";")).map(s -> s.split(",")).toArray(String[][]::new);
        Double[] a1 = Arrays.stream(array[0]).map(Double::parseDouble).toArray(Double[]::new);
        Double[] a2 = Arrays.stream(array[1]).map(Double::parseDouble).toArray(Double[]::new);;
        Double[] a3 = Arrays.stream(array[2]).map(Double::parseDouble).toArray(Double[]::new);;
        Double[] x = Arrays.stream(array[3]).map(Double::parseDouble).toArray(Double[]::new);;
        Double[] b = Arrays.stream(array[4]).map(Double::parseDouble).toArray(Double[]::new);;
        String[] y = array[5];

        double diff1 = a1[0] * x[0] + a1[1] * x[1] + a1[2] * x[2] + a1[3] * x[3] + a1[4] * x[4] - b[0];
        double diff2 = a2[0] * x[0] + a2[1] * x[1] + a2[2] * x[2] + a2[3] * x[3] + a2[4] * x[4] - b[1];
        double diff3 = a3[0] * x[0] + a3[1] * x[1] + a3[2] * x[2] + a3[3] * x[3] + a3[4] * x[4] - b[2];

        boolean flag = compareWithZero(diff1,y[0])
                        && compareWithZero(diff2,y[1])
                        && compareWithZero(diff3,y[2]);
        double maxDiff = Math.max(Math.max(diff1,diff2),diff3);
        System.out.println(flag + " " + (int)Math.floor(maxDiff));
    }

    private boolean compareWithZero(double val, String constriant) {
        boolean flag = false;
        switch (constriant){
            case ">":
                flag = val > 0;
                break;
            case ">=":
                flag = val >= 0;
                break;
            case "<":
                flag = val < 0;
                break;
            case "<=":
                flag = val <= 0;
                break;
            case "=":
                flag = val == 0;
                break;
        }
        return flag;
    }

    /**
     * TODO: 这个题还是不明白
     */
    @Test
    public void weAreATeam(){
//        int n = 5; // 人数
//        int m = 7; // 消息条数
//        int[][] msgs = new int[][]{
//                {1,2,0},
//                {4,5,0},
//                {2,3,0},
//                {1,2,1},
//                {2,3,1},
//                {4,5,1},
//                {1,5,1}
//        };

        int n = 5; // 人数
        int m = 6; // 消息条数
        int[][] msgs = new int[][]{
                {1,2,0},
                {1,2,1},
                {1,5,0},
                {2,3,1},
                {2,5,1},
                {1,3,2}
        };

        getResult_WeAreATeam(msgs,n,m);
    }

    private void getResult_WeAreATeam(int[][] msgs, int n, int m) {
        if (n < 1 || n >= 100000 || m < 1 || m >= 100000) {
            System.out.println("Null");
            return;
        }
        UnionFindSet ufs = new UnionFindSet(n+1);
        Arrays.sort(msgs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        for (int[] msg : msgs) {
            int a = msg[0], b = msg[1], c = msg[2];
            if (a < 1 || a > n || b < 1 || b > n) {
                System.out.println("da pian zi");
                continue;
            }
            if(c == 0){
                ufs.union(a,b);
            }else if(c == 1){
                if(ufs.find(a) == ufs.find(b)){
                    System.out.println("We are a team");
                }else{
                    System.out.println("We are not a team");
                }
            }else{
                System.out.println("da pian zi");
            }
        }
    }


    /**
     * 仅包含小写字母
     */
    @Test
    public void test字母异位词分组(){
//        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        String[] strs = {"bur","rub"};

        // 第一种办法，排序
        Collection<List<String>> values1 = Arrays.stream(strs).collect(Collectors.groupingBy(str -> {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            return new String(chars);
        })).values();
        System.out.println(values1);

        // 第二种办法，统计每个字符的数量，并把字符和数量合并到一起作为key
        Collection<List<String>> values2 = Arrays.stream(strs).collect(Collectors.groupingBy(str -> {
            

            // keypoint 由于数组类型没有重写hashcode和equals，所以不能直接作为HashMap的key进行聚合
//            char[] chars = str.toCharArray();
//            Map<Character, Integer> map = new HashMap<>();
//            for (int i = 0; i < chars.length; i++) {
//                char c = chars[i];
//                map.put(c, map.getOrDefault(c, 0) + 1);
//            }
//
//            StringBuilder sb = new StringBuilder();
//            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//                sb.append(entry.getKey()).append(entry.getValue());
//            }

            int[] count = new int[26];
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                count[c-'a']++; // 由于仅包含小写字母，所以这里字符减去'a'，得到两者之间的差值，作为数组的下标
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if(count[i] != 0){
                    sb.append((char)('a'+i)); // 这里加上'a'，能到得到原来的字符
                    sb.append(count[i]);
                }
            }

            System.out.println(sb.toString());
            return sb.toString();
        })).values();
        System.out.println(values2);
    }

    @Test
    public void test最长连续序列(){
        int[] nums = {100,4,200,1,3,2};
//        int[] nums = {1,2,0,1};
//        int[] nums = {9,1,4,7,3,-1,0,5,8,-1,6};

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longestStreak = 0;

        // 判断当前数的前一个数和后一个数是否在连续序列中
        for (Integer num : set) {
            int currentNum = num;

            // 当前数的前一个数不在集合中，则进行判断后面的数
            if(!set.contains(currentNum-1)){
                int currentStreak = 1;

                // 判断当前数的下一个数是否在集合中，如果在集合中，则长度加1；如果不在，则结束
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                longestStreak = Math.max(longestStreak,currentStreak);
            }
        }
        System.out.println(longestStreak);
    }

    @Test
    public void 字符串转数字(){
        String str = "123";
        if(StringUtils.isBlank(str)){
            return ;
        }
        // 如果不是数字，直接报错

        // 如果超限也要报错

        int result = 0;
        int n = str.length()-1;
        while(n >= 0){
            int c = str.charAt(n) - '0';
            if(n == str.length()-1){
                result = c;
            }else{
                result += c * 10;
            }
            n--;
        }
        System.out.println(result);
    }

}
