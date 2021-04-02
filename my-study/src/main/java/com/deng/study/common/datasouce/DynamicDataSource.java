package com.deng.study.common.datasouce;

import lombok.Getter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/21 13:47
 */

@Getter
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Lock lock = new ReentrantLock();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object,Object> targetDataSources){
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
//        lock.lock();
//        try {
//            counter++;
//            int lookupNumber = counter % getDataSourceNumber();
//            return "dataSource_slave" + lookupNumber;
//        }finally {
//            lock.unlock();
//        }
        return DataSourceHandler.getDataSource();
    }
}
