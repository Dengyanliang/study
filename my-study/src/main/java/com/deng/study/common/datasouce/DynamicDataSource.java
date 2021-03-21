package com.deng.study.common.datasouce;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/21 13:47
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object,Object> targetDataSources){
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHandler.getDataSource();
    }
}
