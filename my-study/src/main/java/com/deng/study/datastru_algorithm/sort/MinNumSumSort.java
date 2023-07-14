package com.deng.study.datastru_algorithm.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/12 20:41
 */
public class MinNumSumSort {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
        String s = "21,30,62,5,31";
        String[] nums = s.split(",");

        System.out.println(getMinNumSum(nums));
    }

    private static String getMinNumSum(String[] nums) {
        Arrays.sort(nums, Comparator.comparingInt(Integer::parseInt));
        System.out.println(Arrays.toString(nums));

        String[] tmp = Arrays.copyOfRange(nums,0,Math.min(3, nums.length));
        System.out.println(Arrays.toString(tmp)); // [5, 21, 30]

        // 这里按数字默认排序
        Arrays.sort(tmp, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                System.out.println("o1:" + o1 + "，o2:" + o2 + "--->" + (o1 + o2));
                System.out.println("o2:" + o2 + "，o1:" + o1 + "--->" + (o2 + o1));
                return (o1 + o2).compareTo(o2 + o1);
            }
        });

        System.out.println(Arrays.toString(tmp)); // [21, 30, 5]

        StringBuilder builder = new StringBuilder();
        for (String s : tmp) {
            builder.append(s);
        }

        return builder.toString();
    }


}
