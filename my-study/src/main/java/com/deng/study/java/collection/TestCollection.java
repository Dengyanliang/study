package com.deng.study.java.collection;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class TestCollection {

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list = list.subList(0, 3); // 直接按照索引位置去取，不用-1

        System.out.println(list); // [1, 2, 3]
    }

    @Test
    public void testMap() {
        ConcurrentMap<Object, Object> map = Maps.newConcurrentMap();
        if (MapUtils.isEmpty(map)) {
            System.out.println("---------2123----------");
        }
    }

    @Test
    public void testSet() {
        List<String> list = new ArrayList<>();
        list.add("10");
        list.add("1");
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");

        Set set = null;
        set = list.stream().collect(Collectors.toSet()); // 无序 [1, lisi, zhangsan, wangwu, 10]
        System.out.println(set);

        Set set2 = null;
        set2 = list.stream().collect(Collectors.toCollection(TreeSet::new)); // 按字典排序 [1, 10, lisi, wangwu, zhangsan]
        System.out.println(set2);

        Set set3 = null;
        set3 = list.stream().collect(Collectors.toCollection(LinkedHashSet::new)); // 按插入排序 [10, 1, zhangsan, lisi, wangwu]
        System.out.println(set3);

        Set set4 = new HashSet();
        boolean contains = set4.contains(1);
        System.out.println(contains);
    }
}
