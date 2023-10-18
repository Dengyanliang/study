package com.deng.shardingsphere.dynamic.datasource.aspect;

import com.deng.shardingsphere.dynamic.datasource.annotation.MyDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Desc: 动态切换数据源切面 第一种方法
 * @Auther: dengyanliang
 * @Date: 2021/3/21 14:11
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect implements Ordered {

    @Before("@annotation(myDS)")
    public void before(MyDS myDS){
        log.info("*******before *************");
        String dbName = myDS.name();
        HintManager.getInstance().setDatabaseShardingValue(dbName);
    }

    @After("@annotation(myDS)")
    public void after(MyDS myDS){
        log.info("*******after*************");
        HintManager.clear();
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
