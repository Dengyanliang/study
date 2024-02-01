package com.deng.study.designpattern.behavioral.chainofresponsibility.two;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Desc: 责任链模式
 * @Author: dengyanliang
 * @Date: 2022-12-08 21:08:54
 */
@Component
@Slf4j
public class FilterChain implements ApplicationContextAware {

    /**
     * key：FilterInterface 实现类对应的bean名字
     * value：FilterInterface 实现类
     */
    Map<String,FilterInterface<FilterRequest,FilterResponse>> beanMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,FilterInterface> beanOfTypes = applicationContext.getBeansOfType(FilterInterface.class);
        if(MapUtils.isNotEmpty(beanOfTypes)){
            for (Map.Entry<String, FilterInterface> entry : beanOfTypes.entrySet()) {
                FilterInterface value = (FilterInterface<FilterRequest,FilterResponse>)entry.getValue();
                beanMap.putIfAbsent(entry.getKey(),value);
            }
        }
        log.info("FilterChain begin init:beanMap:{}", JSON.toJSONString(beanMap));
    }

    public boolean match(FilterRequest filterRequest){
        return true;
    }

    public FilterResponse filter(FilterRequest filterRequest) throws Exception {
        if(match(filterRequest) && (MapUtils.isNotEmpty(beanMap))){
            List<FilterInterface<FilterRequest, FilterResponse>> filterList = beanMap.values().stream().sorted(Comparator.comparing(FilterInterface::order)).collect(Collectors.toList());
            log.info("当前满足条件的过滤器:{}",JSON.toJSONString(filterList));
            for (FilterInterface<FilterRequest, FilterResponse> filter : filterList) {
                filter.doFilter(filterRequest);
            }
        }
        return null;
    }

}
