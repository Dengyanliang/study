package com.deng.jta.bitronix.xa.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.deng.jta.bitronix.xa.properties.DB2Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.bitronix.BitronixXADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 20:44
 */
@Configuration
@MapperScan(basePackages = "com.deng.jta.bitronix.xa.mapper.db2",sqlSessionTemplateRef = "db2SqlSessionTemplate")
public class DB2DataSourceConfig {

    @Resource
    private DB2Properties db2Properties;

    @Bean("db2DS")
    public DataSource db2DataSource() throws Exception {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setURL(db2Properties.getUrl());
        mysqlXADataSource.setUser(db2Properties.getUsername());
        mysqlXADataSource.setPassword(db2Properties.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        BitronixXADataSourceWrapper bitronixXADataSourceWrapper = new BitronixXADataSourceWrapper();
        return bitronixXADataSourceWrapper.wrapDataSource(mysqlXADataSource);
    }

    @Bean("db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DS") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean("db2SqlSessionTemplate")
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
