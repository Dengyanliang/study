package com.deng.study.designpattern.proxy.db;

/**
 * @Desc:
 * @Date: 2023/12/29 19:55
 * @Auther: dengyanliang
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDBType(String dbType){
        CONTEXT_HOLDER.set(dbType);
    }

    public static String getDBType(){
        return CONTEXT_HOLDER.get();
    }
    public static void clearDBType(){
        CONTEXT_HOLDER.remove();
    }
}
