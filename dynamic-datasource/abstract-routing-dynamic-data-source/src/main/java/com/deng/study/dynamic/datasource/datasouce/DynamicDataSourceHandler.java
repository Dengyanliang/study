package com.deng.study.dynamic.datasource.datasouce;

import com.deng.study.dynamic.datasource.constant.DataSourceConsts;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc: 动态数据源上下文
 * @Auther: dengyanliang
 * @Date: 2021/3/21 13:48
 */
@Slf4j
public class DynamicDataSourceHandler {

    /**
     * 存放当前线程使用的数据源名称信息
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 存放所有数据源的名称
     */
    private static final Set<String> DATA_SOURCE_NAME_SET = new HashSet<>();

    /**
     * 存放从库数据源的名称
     */
    private static final List<String> SLAVE_DATA_SOURCE_NAME_SET = new ArrayList<>();

    /**
     * 计数，为了轮询计算，获取其中一个从库数据源
     */
    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 设置当前数据源
     * @param dsName
     */
    public static void setCurrentDataSource(String dsName){
        CONTEXT_HOLDER.set(dsName);
    }

    /**
     * 设置主库数据源
     */
    public static void setMasterDataSource(){
        CONTEXT_HOLDER.set(DataSourceConsts.MASTER);
    }

    /**
     * 设置从库数据源
     */
    public static void setSlaveDataSource(){
        CONTEXT_HOLDER.set(getSlaveDataSource());
    }

    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    public static void removeCurrentDataSource(){
        CONTEXT_HOLDER.remove();
    }

    public static void addDataSourceName(String name){
        DATA_SOURCE_NAME_SET.add(name);
    }

    public static void addSlaveDataSourceName(String name){
        SLAVE_DATA_SOURCE_NAME_SET.add(name);
    }


    public static boolean existDataSourceName(String name){
        return DATA_SOURCE_NAME_SET.contains(name);
    }

    public static int getSlaveDataSourceCount(){
        return SLAVE_DATA_SOURCE_NAME_SET.size();
    }

    /**
     * 获取从库数据源，这里使用轮询算法
     * @return
     */
    public static String getSlaveDataSource(){
        if(counter.get() >= SLAVE_DATA_SOURCE_NAME_SET.size()){
            counter.set(0); // 超过从库集合数，则从0开始
        }
        int num = counter.getAndIncrement() + 1;
//        int num = counter.getAndIncrement() % DynamicDataSourceHandler.getSlaveDataSourceCount() + 1;

        return DataSourceConsts.SLAVE_PREFIX + num;
    }

}
