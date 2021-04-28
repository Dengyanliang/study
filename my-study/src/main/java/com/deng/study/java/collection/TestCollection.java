package com.deng.study.java.collection;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class TestCollection {

    @Test
    public void test(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list = list.subList(0,3); // 直接按照索引位置去取，不用-1

        System.out.println(list); // [1, 2, 3]
    }

    @Test
    public void testMap(){
        ConcurrentMap<Object, Object> map = Maps.newConcurrentMap();
        if(MapUtils.isEmpty(map)){
            System.out.println("---------2123----------");
        }
    }
}
