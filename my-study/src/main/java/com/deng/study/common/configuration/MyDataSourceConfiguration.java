package com.deng.study.common.configuration;

import com.deng.study.common.datasouce.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc: 数据源配置
 * @Auther: dengyanliang
 * @Date: 2021/3/21 20:36
 */
@Configuration
@Component
public class MyDataSourceConfiguration {

    @Bean("masterDsProperties")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean("masterDataSource")
    public DataSource masterDataSource(@Qualifier("masterDsProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("slaveDsProperties1")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSourceProperties slaveDataSourceProperties1(){
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource1")
    public DataSource slaveDataSource1(@Qualifier("slaveDsProperties1") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("slaveDsProperties2")
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSourceProperties slaveDataSourceProperties2(){
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource2")
    public DataSource slaveDataSource2(@Qualifier("slaveDsProperties2") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("slaveDsProperties3")
    @ConfigurationProperties(prefix = "spring.datasource.slave3")
    public DataSourceProperties slaveDataSourceProperties3(){
        return new DataSourceProperties();
    }

    @Bean("slaveDataSource3")
    public DataSource slaveDataSource(@Qualifier("slaveDsProperties3") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

//    public MapDataSourceLookup mapDataSourceLookup( DataSource slaveDataSource1,DataSource slaveDataSource2,DataSource slaveDataSource3){
//        Map<String, DataSource> mapDataSourceLookup = new HashMap<>();
//        mapDataSourceLookup.put("dataSource_slave1",slaveDataSource1);
//        mapDataSourceLookup.put("dataSource_slave2",slaveDataSource2);
//        mapDataSourceLookup.put("dataSource_slave3",slaveDataSource3);
//        return new MapDataSourceLookup(mapDataSourceLookup);
//    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource1,DataSource slaveDataSource2,DataSource slaveDataSource3){

        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("dataSource_master",masterDataSource);
        targetDataSources.put("dataSource_slave0",slaveDataSource1);
        targetDataSources.put("dataSource_slave1",slaveDataSource2);
        targetDataSources.put("dataSource_slave2",slaveDataSource3);

        return new DynamicDataSource(masterDataSource,targetDataSources);
    }
}
