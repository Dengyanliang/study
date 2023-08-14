package com.deng.study.shardingsphere.common.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @Desc: 支持自定义sql注入方法
 * @Auther: dengyanliang
 * @Date: 2023/8/14 11:55
 */
public class CustomSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        // 获取父类SQL注入方法列表
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 将批量插入方法添加进去
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}
