package com.deng.study.dynamic.datasource.interceptor;

import com.deng.study.dynamic.datasource.datasouce.DynamicDataSourceHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: 读写分离Mybatis拦截器，动态切换数据源
 *        TODO: 怎么实现在读写分离的时候，强制查询走主库？
 * @Auther: dengyanliang
 * @Date: 2023/10/17 19:56
 */
@Slf4j
@Order(-2)
@Component
@Intercepts({
        @Signature(type = Executor.class,method = "query",
                args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class ReadWriteSplitInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 根据签名指定的args顺序获取具体的实现类
        // 1. 获取MappedStatement实例, 并获取当前SQL命令类型
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 2. 获取当前正在被操作的类, 有可能是Java Bean, 也可能是普通的操作对象, 比如普通的参数传递
        // 普通参数, 即是 @Param 包装或者原始 Map 对象, 普通参数会被 Mybatis 包装成 Map 对象
        // 即是 org.apache.ibatis.binding.MapperMethod$ParamMap
        Object parameter = invocation.getArgs()[1];

        // 3. 获取拦截器指定的方法类型
        String methodName = invocation.getMethod().getName();

        BoundSql sql = mappedStatement.getBoundSql(parameter);
        List<ParameterMapping> list = sql.getParameterMappings();
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(sql.getParameterObject());
        List<Object> params = new ArrayList<>(list.size());
        for (ParameterMapping mapping : list) {
//            params.add(Ognl.getValue(Ognl.parseExpression(mapping.getProperty()), context, context.getRoot()));
        }

        System.out.println("-----> sql: " + sql.getSql() + "\n----->methodName："+ methodName +"\n-----> args: " + params);

        // 所有查询类型的语句全部走从库
        if(mappedStatement.getSqlCommandType() == SqlCommandType.SELECT){
            log.info("******* ReadWriteSplitInterceptor set datasource_slave ******");
            DynamicDataSourceHandler.setSlaveDataSource();
            try {
                return invocation.proceed();
            } finally {
                DynamicDataSourceHandler.removeCurrentDataSource();
            }
        }else{
            log.info("******* ReadWriteSplitInterceptor set datasource_master ******");
            return invocation.proceed();
        }
    }

    /**
     * plugin 用来设置Mybatis 什么情况下需要进行拦截
     * 默认实现是每次都进行拦截，都会执行intercept方法
     * @param target
     * @return
     */
    public Object plugin(Object target){
        // 拦截 Executor 执行器
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        return target;
    }


}
