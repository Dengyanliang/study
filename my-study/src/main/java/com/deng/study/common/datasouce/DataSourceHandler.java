package com.deng.study.common.datasouce;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/21 13:48
 */
@Slf4j
public class DataSourceHandler {

    public static final String  DATASOURCE_MASTER = "dataSource_master";
    public static final String  DATASOURCE_SLAVE = "dataSource_slave";

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDataSource(String value){
        threadLocal.set(value);
    }

    public static String getDataSource(){
        log.info("getDataSource:{}",threadLocal.get());
        return threadLocal.get();
    }

    public static void removeDataSource(){
        Thread thread = Thread.currentThread();
        log.info("current thread:{}",thread);
        threadLocal.remove();
    }
}
