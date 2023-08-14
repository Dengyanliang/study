package com.deng.study.shardingsphere.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc: mybatis-plus
 * @Auther: dengyanliang
 * @Date: 2023/8/13 21:05
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页配置，不配置不会生效
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自定义批量插入的类
     * @return
     */
    @Bean
    public CustomSqlInjector customSqlInjector(){
        return new CustomSqlInjector();
    }

}
