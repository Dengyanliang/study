package com.deng.seata.tcc.account.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @Desc: 数据源配置
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:41
 */
@Configuration
public class DataSourceConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    // Seata是通过代理数据源实现事务分支的，所以需要配置io.seata.rm.datasource.DataSourceProxy的Bean，
    // 且是@Primary默认的数据源，否则事务不会回滚，无法实现分布式事务
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }
}
