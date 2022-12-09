package com.deng.study.java.design.filter;

import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-08 21:11:35
 */
@Component("filterHandler1")
public class FilterHandler1 implements FilterInterface<FilterRequest,FilterResponse>{
    @Override
    public FilterResponse doFilter(FilterRequest filterRequest) throws Exception {
        System.out.println("FilterHandler1 执行");
        return null;
    }

    @Override
    public int order() {
        return 10;
    }
}
