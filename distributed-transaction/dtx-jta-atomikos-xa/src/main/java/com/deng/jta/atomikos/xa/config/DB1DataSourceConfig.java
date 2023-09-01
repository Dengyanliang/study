package com.deng.jta.atomikos.xa.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.deng.jta.atomikos.xa.properties.DB1Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 20:44
 */
@Configuration
@MapperScan(basePackages = "com.deng.jta.atomikos.xa.mapper.db1",sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class DB1DataSourceConfig {

    @Autowired
    private DB1Properties db1Properties;

    @Primary
    @Bean("db1DS")
    public DataSource db1DataSource(){
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setURL(db1Properties.getUrl());
        mysqlXADataSource.setUser(db1Properties.getUsername());
        mysqlXADataSource.setPassword(db1Properties.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("db1DS");
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        return atomikosDataSourceBean;
    }

    @Bean("db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean("db1SqlSessionTemplate")
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}