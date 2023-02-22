package com.deng.study.java.thread.juc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/18 20:58
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        Map<String,LongAdder> map = new ConcurrentHashMap<>();
        String key = "key";

        for (int i = 0; i < 10; i++) {
            LongAdder longAdder = map.computeIfAbsent(key, (value) ->new LongAdder());
            longAdder.increment();
        }

        System.out.println(map.get(key));

    }
}
