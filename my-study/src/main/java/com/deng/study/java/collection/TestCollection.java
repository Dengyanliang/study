package com.deng.study.java.collection;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        Map tempMap = new HashMap();
        tempMap.put(null,1);
        Object o = tempMap.get(null);
        System.out.println("null test:"+o);

        ConcurrentMap<Object, Object> map = Maps.newConcurrentMap();
        if (MapUtils.isEmpty(map)) {
            System.out.println("---------2123----------");
        }

        // 不用判断直接添加
        Map<String, Collection<String>> mapList = new HashMap<>();
        mapList.computeIfAbsent("d",p->new ArrayList<>()).add("1");
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

    @Test
    public void testList(){
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        list2.add("7");
        list2.add("8");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---交集 intersection---");
        intersection.parallelStream().forEach(System.out::print);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::print);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::print);

        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---并集 listAll---");
        listAll.parallelStream().forEachOrdered(System.out::print);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEachOrdered(System.out::print);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEachOrdered(System.out::print);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEachOrdered(System.out::print);


        List<String> list3 = new ArrayList<String>();
        List<String> list4 = new ArrayList<String>();

        //交集
        list3.addAll(list1);
        list4.addAll(list2);
        list3.retainAll(list4);
        System.out.println("两个list进行交集操作：" + list3.toString());

        //并集
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        list3.addAll(list1);
        list4.addAll(list2);
        list3.addAll(list4);
        System.out.println("两个list进行并集操作：" + list3.toString());

        //差集
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        list3.addAll(list1);
        list4.addAll(list2);
        list3.removeAll(list4);
        for (String y : list3){
            System.out.println(y);
        }
        System.out.println("两个list进行差集操作：" + list3.toString());

        //去重复并集
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        list3.addAll(list1);
        list4.addAll(list2);
        list4.removeAll(list3);
        list3.addAll(list4);
        System.out.println("两个list进行去重复并集操作：" + list3.toString());

    }
}
