package com.deng.study.datastru_algorithm.leetcode;

import com.deng.common.util.MyArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @desc:
 * @version: dengyanliang
 * @date: 2024/6/28
 */
public class 合并区间 {

    public static void main(String[] args) {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] result = null;
        if(intervals == null){
            result =  new int[0][2];
        }
        // 对所有的左区间进行升序排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });

        // 放置一维数组，每个数组中有两个值
        List<int[]> merged = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            // 取出每一对的左右区间
            int left = intervals[i][0],right = intervals[i][1];
            // result.size() == 0 表示还没有放入值，此时把第一个放入进去
            // result.get(result.size()-1)[1] < left 集合中最后一个值的右区间小于当前的左区间，说明是按照顺序的，直接放入
            if(merged.size() == 0 || merged.get(merged.size()-1)[1] < left){
                merged.add(new int[]{left,right});
            }else{ // 否则，比较集合中最后一个数组的右区间和当前的右区间，取出最大值，作为集合中最后一个区间的右区间
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1],right);
            }
        }

        result = merged.toArray(new int[merged.size()][]);
        MyArrayUtil.printArray(result);
    }
}
