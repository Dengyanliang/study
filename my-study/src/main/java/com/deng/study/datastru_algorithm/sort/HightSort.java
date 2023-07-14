package com.deng.study.datastru_algorithm.sort;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @Desc: 按身高的绝对值进行从小到达排序
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/12 20:19
 */
public class HightSort {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int h = scanner.nextInt();
//        int n = scanner.nextInt();
//        Integer[] hights = new Integer[n];
//        for (int i = 0; i < n; i++) {
//            hights[i] = scanner.nextInt();
//        }

        int h = 100; // 输入的身高
//        int n = 10;
        // 需要对比的身高
        Integer[] hights = new Integer[]{95,96,97,98,99,101,102,103,104,105};

        System.out.println(sort(h, hights));
    }

    private static String sort(int h, Integer[] hights) {
        Arrays.sort(hights,(a,b)->{
            int absA = Math.abs(a-h);
            int absB = Math.abs(b-h);
            if(absA != absB){
                return absA - absB;
            }else{
                return a - b;
            }
        });
        StringJoiner joiner = new StringJoiner(" ");
        for (Integer hight : hights) {
            joiner.add(hight+"");
        }
        return joiner.toString();
    }
}
