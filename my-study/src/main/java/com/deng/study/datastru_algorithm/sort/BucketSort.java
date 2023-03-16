package com.deng.study.datastru_algorithm.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Desc: 桶排序
 * @Auther: dengyanliang
 * @Date: 2023/1/31 10:48
 */
@Slf4j
public class BucketSort {
    private static final int ARRAY_LENGTH = 10000000;

    public static void main(String[] args) {
//        int[] arr = new int[ARRAY_LENGTH];
//        for (int i = 0; i < ARRAY_LENGTH; i++) {
//            arr[i] = (int) (Math.random() * ARRAY_LENGTH);
//        }
        int[] arr = {15, 8, 23, 38, 28, 19, 32, 21, 33, 9};
//        System.out.println("原始数组：" + Arrays.toString(arr));
        long start = System.currentTimeMillis();
        sort(arr);
//        sort2(arr);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
//        System.out.println("排序后：" + Arrays.toString(arr));
    }

    private static void sort2(int[] arr) {
        Arrays.sort(arr);
    }

    private static void sort(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // 1、找出待排序的数组中最大元素max和最小元素min
        for (int num : arr) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        System.out.println("max: " + max);
        System.out.println("min：" + min);

        int bucketNum = (max - min) / arr.length + 1;
        System.out.println("桶的数量：" + bucketNum);

        // 2、根据指定的桶数创建桶，这里的桶使用List，桶里面的数据结构也使用List
        List<List<Integer>> bucketList = new ArrayList<>();
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new ArrayList<>());
        }

        // 3、根据公式遍历数组：桶编号=（数组元素-最小值）/数组长度，把数据放在相应的桶中
        for (int num : arr) {
            int bucketIndex = (num - min) / arr.length;
//            int bucketIndex = (num - min) * (BUCKET_NUM - 1) / (max - min);
//            log.info("({}-{}) * ({}-1)/({}-{})", num, min, BUCKET_NUM, max, min);
//            log.info("【{}】放入到第【{}】号桶中",num,bucketIndex+1);
            List<Integer> list = bucketList.get(bucketIndex);
            list.add(num); // 将数据放入到桶中
        }

        // 4、从小到大遍历每一个桶
        int arrIndex = 0;
        for (int i = 0; i < bucketList.size(); i++) {
            List<Integer> list = bucketList.get(i);
            log.info("第【{}】桶的数据为：{}", i + 1, list);
            Collections.sort(list);// 对桶中的数据进行排序

            // 5、把排好序的元素从索引为0开始放入（前一个桶的所有元素小于后一个桶中的所有元素，并且每个桶中元素有序），完成排序
            for (Integer value : list) {
                arr[arrIndex++] = value;
            }
        }
    }


}
