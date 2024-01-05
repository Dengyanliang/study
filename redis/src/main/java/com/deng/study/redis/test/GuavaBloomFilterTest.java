package com.deng.study.redis.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 09:40
 */
public class GuavaBloomFilterTest {

    public static void main(String[] args) {
        // 创建Guava布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),100);
        // 判断指定的元素是否存在
        System.out.println(bloomFilter.mightContain(1));
        System.out.println(bloomFilter.mightContain(2));

        System.out.println("-----------");
        // 将元素新增至布隆过滤器
        bloomFilter.put(1);
        bloomFilter.put(2);

        System.out.println(bloomFilter.mightContain(1));
        System.out.println(bloomFilter.mightContain(2));

    }
}
