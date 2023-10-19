package com.deng.study.dynamic.datasource.aspect;

import com.deng.study.dynamic.datasource.annotation.MyDS;
import com.deng.study.dynamic.datasource.datasouce.DynamicDataSourceHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Desc: 动态切换数据源切面 第二种方法：切注解
 * @Auther: dengyanliang
 * @Date: 2023/10/18 15:14
 */
@Slf4j
@Order(-1)
@Aspect
@Component
public class DataSourceAspect2 {

    /**
     * @within 作用在类级别
     * 拦截使用了注解MyDS的类的所有方法
     *
     * @param joinPoint
     * @param targetDataSource
     */
    @Before("@within(targetDataSource)")
    public void changeDataSourceFromClassAnnotation(JoinPoint joinPoint, MyDS targetDataSource){
        changeDataSource(joinPoint,targetDataSource);
    }

    /**
     * @annotation 作用在方法级别
     * 拦截使用了注解MyDS的方法
     *
     * @param joinPoint
     * @param targetDataSource
     */
    @Before("@annotation(targetDataSource)")
    public void changeDataSourceFromMethodAnnotation(JoinPoint joinPoint,MyDS targetDataSource){
        changeDataSource(joinPoint,targetDataSource);
    }

    @After("@within(targetDataSource)")
    public void clearDataSourceFromClassAnnotation(MyDS targetDataSource){
        clearDataSource(targetDataSource);
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSourceFromMethodAnnotation(MyDS targetDataSource){
        clearDataSource(targetDataSource);
    }

    private void changeDataSource(JoinPoint joinPoint, MyDS targetDataSource) {
        if(targetDataSource != null){
            String dsName = targetDataSource.name();
            if(targetDataSource.isMaster()){
                log.info("******* DataSourceAspect2 set master datasource *******");
                DynamicDataSourceHandler.setMasterDataSource();
            }else if(DynamicDataSourceHandler.existDataSourceName(dsName)){
                log.info("******* DataSourceAspect2 set current dataSource *******");
                DynamicDataSourceHandler.setCurrentDataSource(dsName);
            }else{
                log.error("DataSourceAspect2 数据源：{} 不存在-->{}",dsName,joinPoint.getSignature());
            }
        }else {
            log.info("******* DataSourceAspect2 set slave datasource *******");
            DynamicDataSourceHandler.setSlaveDataSource();
        }
    }


    private void clearDataSource(MyDS targetDataSource) {
        log.debug("******* DataSourceAspect2 after 清除数据源 {}", targetDataSource.name());
        DynamicDataSourceHandler.removeCurrentDataSource();
    }
}
