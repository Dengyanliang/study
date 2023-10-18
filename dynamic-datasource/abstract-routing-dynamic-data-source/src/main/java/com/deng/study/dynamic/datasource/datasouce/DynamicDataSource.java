package com.deng.study.dynamic.datasource.datasouce;

import lombok.Getter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Desc: 定义动态数据源，通过集成Spring提供的AbstractRoutingDataSource，只需要实现determineCurrentLookupKey方法即可
 *        由于DynamicDataSource是单例的，线程不安全的，所以采用ThreadLocal保证线程安全，由DynamicDataSourceHandler完成
 * @Auther: dengyanliang
 * @Date: 2021/3/21 13:47
 */

@Getter
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHandler.getDataSource();
    }
}
