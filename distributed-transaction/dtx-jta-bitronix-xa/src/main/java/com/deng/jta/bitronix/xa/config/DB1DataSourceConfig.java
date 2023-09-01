package com.deng.jta.bitronix.xa.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.deng.jta.bitronix.xa.properties.DB1Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.bitronix.BitronixXADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 20:44
 */
@Configuration
@MapperScan(basePackages = "com.deng.jta.bitronix.xa.mapper.db1",sqlSessionFactoryRef = "db1SqlSessionFactory")
public class DB1DataSourceConfig {

    @Resource
    private DB1Properties db1Properties;

    @Primary
    @Bean("db1DS")
    public DataSource db1DataSource() throws Exception {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setURL(db1Properties.getUrl());
        mysqlXADataSource.setUser(db1Properties.getUsername());
        mysqlXADataSource.setPassword(db1Properties.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        BitronixXADataSourceWrapper bitronixXADataSourceWrapper = new BitronixXADataSourceWrapper();
        return bitronixXADataSourceWrapper.wrapDataSource(mysqlXADataSource);
    }

    @Bean("db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DS") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean("db1SqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}