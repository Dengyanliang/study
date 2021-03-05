package com.deng.study.java.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc: 第二种实现多线程批量读取集合的方法
 * @Auther: dengyanliang
 * @Date: 2021/1/28 11:45
 */
public class ThreadDemoTest2{

    private static int subSize = 10;

    /**
     * 打印出：
     *  1 4 7 10 13 16 19 22 25
     *  2 5 8 11 14 17 20 23
     *  3 6 9 12 15 18 21 24
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Map<Long,Integer> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>(100);
        for(int i = 1; i <= 25; i++){
            dataList.add(i);
        }

        int length = dataList.size();
        int threadNum = length % subSize == 0 ? length / subSize : length / subSize + 1;
        for(int i = 0; i < threadNum; i++){
            ThreadDemo2 threadDemo2 = new ThreadDemo2(dataList,map,threadNum);
            Thread thread = new Thread(threadDemo2);
            map.put(thread.getId(),i);
            thread.start();

            thread.join();
        }
    }
}

class ThreadDemo2 implements Runnable{
    private List<Integer> dataList;
    private Map<Long, Integer> map;
    private int gap; // 每次间隔多少取一个值

    public ThreadDemo2(List<Integer> dataList, Map<Long, Integer> map, int gap) {
        this.dataList = dataList;
        this.map = map;
        this.gap = gap;
    }

    @Override
    public void run() {
        int i = map.get(Thread.currentThread().getId());
        for(; i < dataList.size(); i+= gap){
            System.out.print(" "+dataList.get(i));
        }
        System.out.println();
    }
}
