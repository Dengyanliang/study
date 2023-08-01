package com.deng.study.datastru_algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Desc: 区间合并排序
 * @Auther: dengyanliang
 * @Date: 2023/7/6 16:04
 */
public class IntervalMergingSort {

    public static void main(String[] args) {
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        intervals.add(new Interval(1,3));
        intervals.add(new Interval(2,6));
        intervals.add(new Interval(8,10));
        intervals.add(new Interval(15,18));

        IntervalMergingSort sort = new IntervalMergingSort();
        ArrayList<Interval> result = sort.merge(intervals);
        System.out.println(result);
    }

    public ArrayList<Interval> merge (ArrayList<Interval> intervals) {
        // 对所有的左区间进行升序排列
        Collections.sort(intervals,(v1,v2)->v1.start-v2.start);
        // 用于保存结果
        ArrayList<Interval> result = new ArrayList<>();
        int index = -1;
        for (Interval interval : intervals) {
            // index = -1 时，直接把第一个区间的保存到结果集合中
            // interval.start > result.get(index).end 说明当前集合的开始值 大于 上一个集合的结束值，直接保存
            if(index == -1 || interval.start > result.get(index).end){
                result.add(interval);
                index++;
            }else{ // 当前集合的开始值小于上一个集合的结束值，那么就需要比较当前区间的结束值和上一个区间的结束值，找出两者的最大值，作为上一个区间的结束值
                result.get(index).end = Math.max(interval.end,result.get(index).end);
            }
        }
        return result;
    }
}

class Interval {
    int start;
    int end;
    public Interval(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public String toString() {
        return "[" + start +
                ", " + end +
                ']';
    }
}