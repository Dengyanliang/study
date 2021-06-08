package com.deng.study.java.java8.lambda;

import com.deng.study.domain.Apple;

import java.util.Comparator;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/11 11:44
 */
public class AppleComparator implements Comparator<Apple> {
    @Override
    public int compare(Apple o1, Apple o2) {
        // compareTo方法是针对Number类型方法的比较，可用于Byte,Integer,Long等
        return o1.getWeight().compareTo(o2.getWeight());
    }
}
