package com.deng.study.datastru_algorithm.sort;

import com.deng.study.domain.Country;
import com.deng.study.util.ArrayUtil;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/13 09:58
 */
public class SortTest {


    @Test
    public void test1(){
        String str = "1,2,5,-21,22,11,55,-101,42,8,7,32";
        String[] nums = str.split(",");

        Arrays.sort(nums, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
//                char c1 = o1.charAt(o1.length() - 1);
//                System.out.println("o1:" + o1 + ",c1:" + c1);
//
//                char c2 = o2.charAt(o2.length() - 1);
//                System.out.println("o2:" + o2 + ",c2:" + c2);

                return o1.charAt(o1.length()-1)-o2.charAt(o2.length()-1);
            }
        });
        StringJoiner joiner = new StringJoiner(" ");
        for (String num : nums) {
            joiner.add(num);
        }
        String result = joiner.toString();
        System.out.println(result);
    }

    @Test
    public void test2(){
        int n = 2;
        String[] logs = new String[]{
          "01:41:8:9",
          "1:1:09:211"
        };
        Arrays.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (int)(convert(o1) + convert(o2));
            }
        });
        for (String log : logs) {
            System.out.println(log);
        }

    }

    private long convert(String log){
        String reg = "(\\d+):(\\d+):(\\d+):(\\d+)"; //
        Pattern compile = Pattern.compile(reg); // 这一步可以提取出来，放到静态变量中
        Matcher matcher = compile.matcher(log);
        if(matcher.find()){
            long H = Long.parseLong(matcher.group(1)) * 60 * 60 * 1000;
            long M = Long.parseLong(matcher.group(2)) * 60 * 1000;
            long S = Long.parseLong(matcher.group(3)) * 1000;
            long N = Long.parseLong(matcher.group(4));
            return H + M + S + N;
        }else{
            return 0;
        }
    }

    @Test
    public void 比赛(){
        int[][] scores = new int[][]{
                {10,6,9,7,6},
                {9,10,6,7,5},
                {8,10,6,5,10},
                {9,10,8,4,9}
        };
        int m = scores.length; // 评委数 行数
        int n = scores[0].length; // 选手数 列数

        System.out.println(getResult_比赛(m, n, scores));
    }

    private String getResult_比赛(int m,int n,int[][] scores) {
        if(m < 3 || m > 10 || n < 3 || n > 10){
            return "-1";
        }

        // 存放每个选手，对应的成绩 key:选手索引 value:选手对应的所有成绩
        HashMap<Integer, Integer[]> players = new HashMap<>();

        // 计算每个选手的成绩，总共有n个选手，每个选手有m个成绩
        for (int j = 0; j < n; j++) {
            // 每个选手对应的成绩
            Integer[] player = new Integer[m];
            for (int i = 0; i < m; i++) {
                if(scores[i][j] > 10 || scores[i][j] < 1){
                    return "-1";
                }
                player[i] = scores[i][j];
            }

            Arrays.sort(player, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

            System.out.println(Arrays.toString(player));
            // key的值从1开始
            players.put(j+1, player);
        }

        StringJoiner sj = new StringJoiner(",");
        players.entrySet().stream().sorted(new Comparator<Map.Entry<Integer, Integer[]>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer[]> o1, Map.Entry<Integer, Integer[]> o2) {
                Integer[] playerA = o1.getValue();
                Integer[] playerB = o2.getValue();

                int sumA = sum(playerA);
                int sumB = sum(playerB);

                if(sumA != sumB){
                    return sumB - sumA;
                }else{
                    for (int i = 0; i < m; i++) {
                        if(playerA[i].equals(playerB[i])) {
                            System.out.println(playerA[i]);
                            continue;
                        }
                        else
                            return playerB[i] - playerA[i];
                    }
                }
                return 0;
            }
        }).limit(3).forEach(p->sj.add(p.getKey()+""));

        return sj.toString();
    }

    private int sum(Integer[] array){
        return Arrays.stream(array).reduce(Integer::sum).orElse(0);
    }

    @Test
    public void 身高体重(){
        int[] heights = {100,100,120,130};
        int[] weights = {40,30,60,50};
        int n = 4;
        String result = getResult_身高体重(n, heights, weights);
        System.out.println(result);
    }

    private String getResult_身高体重(int n,int[] heights, int[] weights) {
        int[][] students = new int[n][3];
        for (int i = 0; i < n; i++) {
            students[i] = new int[]{heights[i],weights[i],i+1};
        }
        ArrayUtil.printArray(students);

        Arrays.sort(students, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] != o2[0]){ // 身高不相等
                    return o1[0] - o2[0]; // 按身高由低到高排序
                }else{ // 身高相等
                    if(o1[1] != o2[1]){  // 身高相等，体重不相等
                        return o1[1] - o2[1]; // 按体重由轻到重排序
                    }else{  // 身高体重都相等
                        return o1[2] - o2[2]; // 按照默认编号排序
                    }
                }
            }
        });
        StringJoiner sj = new StringJoiner(" ");
        for (int[] student : students) {
            sj.add(student[2]+"");
        }
        return sj.toString();
    }

    @Test
    public void 磁盘容量(){
//        String[] capacitys = new String[]{"1G","2G","1024M"};
        String[] capacitys = new String[]{"2G4M","3M2G","1T"};
        Arrays.sort(capacitys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return calc(o1) - calc(o2);
            }
        });

        for (String capacity : capacitys) {
            System.out.println(capacity);
        }
    }

    private static int calc(String capacity){
        int ans = 0;

        // 遍历每一个字符，都换算成最小单位M
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < capacity.length(); i++) {
            char c = capacity.charAt(i);
            if(c >= '0' && c <= '9'){ // 如果是数字
                num.append(c);
            }else{ // 不是数字，是字符
                switch (c) {
                    case 'M' :
                        ans += Integer.parseInt(num.toString());
                        break;
                    case 'G':
                        ans += Integer.parseInt(num.toString()) * 1024;
                        break;
                    case 'T':
                        ans += Integer.parseInt(num.toString()) * 1024 * 1024;
                        break;
                }
                // 生成新的对象，供下一个字符使用
                num = new StringBuilder();
            }
        }
        return ans;
    }

    @Test
    public void 字符串排序(){
        String[] arr = new String[]{"Hello","hello","world"};

        // 先排序
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        // 用栈去重
        LinkedList<String> stack = new LinkedList<>();
        stack.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            String s = arr[i];
            String add = s.toLowerCase();
            String top = stack.getLast().toLowerCase();

            if(add.equals(top))
                continue;
            stack.add(s);
        }
        StringJoiner sj = new StringJoiner(" ");
        for (String s : stack) {
            sj.add(s);
        }
        System.out.println(sj.toString());
    }

    @Test
    public void 冠亚军(){
        Country[] countries = new Country[]{
          new Country("China",32,28,34),
          new Country("England",12,34,22),
          new Country("France",23,33,2),
          new Country("Japan",12,34,25),
          new Country("Rusia",23,43,0)
        };

        Arrays.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                if(o1.gold != o2.gold){
                    return o2.gold - o1.gold;
                }else{
                    if(o1.silver != o2.silver){
                        return o2.silver - o1.silver;
                    }else{
                        if(o1.bronze != o2.bronze){
                            return o2.bronze - o1.bronze;
                        }else{
                            return o1.name.compareTo(o2.name);
                        }
                    }
                }
            }
        });
        for (Country country : countries) {
            System.out.println(country.name);
        }
    }
}
