package com.deng.study.designpattern.behavioral.chainofresponsibility.two;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-08 21:07:05
 */
public interface FilterInterface<T,R> {

    /**
     * 核心过滤器
     * @param t 入参
     * @return
     * @throws Exception
     */
    R doFilter(T t) throws Exception;

    /**
     * 每个链执行的顺序
     * @return
     */
    int order();

}
