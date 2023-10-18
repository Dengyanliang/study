package com.deng.study.dynamic.datasource.aspect;

import com.deng.study.dynamic.datasource.datasouce.DynamicDataSourceHandler;
import com.deng.study.dynamic.datasource.annotation.MyDS;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * @Desc: 动态切换数据源切面 第一种方法
 * @Auther: dengyanliang
 * @Date: 2021/3/21 14:11
 */
@Slf4j
//@Aspect
//@Component
public class DataSourceAspect implements Ordered {

    @Pointcut("execution(* com..*.*ServiceImpl.*(..))")
    public void DynamicChangeDB(){
        log.info("*******DynamicChangeDB*************");
    }

    @Before("DynamicChangeDB()")
    public void before(JoinPoint joinPoint){
        log.info("*******before *************");
        // 获取注解，根据注解自动设置不同的数据源
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyDS myDS = method.getAnnotation(MyDS.class);
        if(myDS != null){
            String dsName = myDS.name();
            if(myDS.isMaster()){
                log.info("******* DataSourceAspect set master datasource *******");
                DynamicDataSourceHandler.setMasterDataSource();
            }else if(DynamicDataSourceHandler.existDataSourceName(dsName)){
                log.info("******* DataSourceAspect set current dataSource *******");
                DynamicDataSourceHandler.setCurrentDataSource(dsName);
            }else {
                log.error("数据源：{} 不存在-->{}", dsName, joinPoint.getSignature());
            }
        } else{
            log.info("******* DataSourceAspect set slave datasource *******");
            DynamicDataSourceHandler.setSlaveDataSource();
        }
    }

    @After("execution(* com..*.*ServiceImpl.*(..))")
    public void after(){
        log.info("*******after*************");
        DynamicDataSourceHandler.removeCurrentDataSource();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
