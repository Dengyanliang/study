package com.deng.study.common.datasouce;

import com.deng.study.common.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc:数据源切面
 * @Auther: dengyanliang
 * @Date: 2021/3/21 14:11
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    private static AtomicInteger counter = new AtomicInteger(0);
    private final int dataSourceNumber = 3;

    @Pointcut("execution(* com..*.*ServiceImpl.*(..))")
    public void DynamicChangeDB(){
        log.info("*******DynamicChangeDB*************");
    }

    @Before("DynamicChangeDB()")
    public void before(JoinPoint point){
        log.info("*******before*************");
        // 获取注解，根据注解自动设置不同的数据源
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if(dataSource == null || dataSource.isMaster()){ // 为空情况下，默认设置为主库
            log.info("*******set datasource_master*************");
            DataSourceHandler.setDataSource(DataSourceHandler.DATASOURCE_MASTER);
        }else{
//            synchronized (DataSourceAspect.class){
//                counter++;
//            }
            int num = counter.getAndIncrement() % dataSourceNumber;
            log.info("*******set datasource_slave*************");
            DataSourceHandler.setDataSource(DataSourceHandler.DATASOURCE_SLAVE_PREFIX+num);
        }
    }

    @After("execution(* com..*.*ServiceImpl.*(..))")
    public void after(){
        log.info("*******after*************");
        DataSourceHandler.removeDataSource();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
