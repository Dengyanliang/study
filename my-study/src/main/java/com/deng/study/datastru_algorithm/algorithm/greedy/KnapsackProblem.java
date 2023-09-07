package com.deng.study.datastru_algorithm.algorithm.greedy;


import com.deng.common.util.MyArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Desc: 贪心算法解决背包问题
 * @Auther: dengyanliang
 * @Date: 2023/3/17 21:12
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        int[] weights = {35, 30, 60, 50, 40, 10, 25};
        int[] values = {10, 40, 30, 50, 35, 40, 30};
        int capacity = 150;

        List<Integer> result = maxValueList(weights, values, capacity);
        System.out.println(result);
    }

    private static List<Integer> maxValueList(int[] weights, int[] values, int capacity) {
        int n = values.length;
        List<Integer> result = new ArrayList<>();

        // 1、计算单价
        double[][] unitValues = new double[n][2]; // 第一位是当前索引，第二位是索引对应的单价
        for (int i = 0; i < n; i++) {
            double unitValue = (double) values[i] / (double) weights[i];
            unitValues[i] = new double[]{i, unitValue};
        }
        // 打印单价数组的结果
        MyArrayUtil.printArray(unitValues);

        // 2、对单价数组结果按单价从大到小进行排序
        Arrays.sort(unitValues, (o1, o2) -> Double.compare(o2[1], o1[1]));

        System.out.println("----------");
        MyArrayUtil.printArray(unitValues);

        // 3、遍历传入的数组，每次取单价最大物品放入
        int sumWeight = 0;
        double sumValue = 0.0;
        for (int i = 0; i < n; i++) {
            // 根据索引到二维数组中查询
            int currIndex = (int) unitValues[i][0];
            double currUnitValue = unitValues[i][1];

            result.add(currIndex);

            if (sumWeight + weights[currIndex] < capacity) {
                sumValue += values[currIndex];
                sumWeight += weights[currIndex];
            } else {
                sumValue += currUnitValue * (capacity - sumWeight);
                sumWeight = capacity; // 背包已满，直接退出
                break;
            }
        }

        System.out.println("最大背包容量是：" + sumWeight + "，最大背包价值是：" + sumValue);
        return result;
    }


}
