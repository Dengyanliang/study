package com.deng.employee;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Desc: 用于控制分布式事务
 * @Auther: dengyanliang
 * @Date: 2023/12/22 12:22
 */
@Configuration
public class DatabaseConfig {

    /**
     * 配置数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }


    /**
     * 配置数据源的代理
     * @param druidDataSource
     * @return
     */
    @Primary
    @Bean("datasource")
    public DataSource dataSource(DruidDataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

}
