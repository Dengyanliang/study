package com.deng.study.java.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
