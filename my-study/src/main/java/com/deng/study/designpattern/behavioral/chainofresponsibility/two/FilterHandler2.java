package com.deng.study.designpattern.behavioral.chainofresponsibility.two;

import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-08 21:11:35
 */
@Component("filterHandler2")
public class FilterHandler2 implements FilterInterface<FilterRequest,FilterResponse>{

    @Override
    public FilterResponse doFilter(FilterRequest filterRequest) throws Exception {
        System.out.println("FilterHandler2 执行");
        return null;
    }

    @Override
    public int order() {
        return 20;
    }
}
