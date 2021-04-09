package com.deng.study.java.java8;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 21:00
 */
@FunctionalInterface
public interface MyPredicate<T> {
    boolean test(T t);
}
