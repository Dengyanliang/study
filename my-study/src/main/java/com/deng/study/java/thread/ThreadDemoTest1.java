package com.deng.study.java.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/28 11:45
 */
public class ThreadDemoTest1{

    private static int subSize = 10;

    /**
     * 打印出：
     * subList:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     * subList:[21, 22, 23, 24, 25]
     * subList:[11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
     * @param data
     */
    private static void handle(List<Integer> data){
        int length = data.size();
        int threadNum = length % subSize ==0 ? length / subSize : length / subSize + 1;
        for(int i = 0; i < threadNum; i++){
            int start = i * subSize;
            int end = (i+1) * subSize;
            end = Math.min(end, length); // 取最后的索引，如果最后的索引比集合长度大，则取集合最后的长度，否则取计算出来的值

            ThreadDemo demo = new ThreadDemo(data,start,end);
            Thread thread = new Thread(demo);
            thread.start();
        }
    }

    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<>(1000);
        for(int i = 1; i <= 25; i++){
            dataList.add(i);
        }
        handle(dataList);
    }
}

class ThreadDemo implements Runnable{
    private List<Integer> dataList;
    private int start;
    private int end;

    public ThreadDemo(List<Integer> dataList, int start, int end) {
        this.dataList = dataList;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        List<Integer> subList = dataList.subList(start,end); // 左闭右开区间
        System.out.println("subList:"+subList);
    }
}
