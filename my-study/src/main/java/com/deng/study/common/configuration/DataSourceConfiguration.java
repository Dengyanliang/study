package com.deng.study.common.configuration;

import com.deng.study.common.datasouce.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 数据源配置
 * @Auther: dengyanliang
 * @Date: 2021/3/21 20:36
 */
@Configuration
@Component
public class DataSourceConfiguration {

//    @Primary
    @Bean("masterDsProperties")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }


//    @Primary
    @Bean("masterDataSource")
    public DataSource masterDataSource(@Qualifier("masterDsProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("slaveDsProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource")
    public DataSource slaveDataSource(@Qualifier("slaveDsProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("dataSource_master",masterDataSource);
        targetDataSources.put("dataSource_slave",slaveDataSource);
        return new DynamicDataSource(masterDataSource,targetDataSources);
    }
}
