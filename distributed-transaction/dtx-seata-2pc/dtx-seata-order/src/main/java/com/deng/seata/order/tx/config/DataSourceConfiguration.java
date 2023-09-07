package com.deng.seata.order.tx.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 依赖 seata-spring-boot-starter 时，自动代理数据源，无需额外处理。
 * 依赖 seata-all 时，使用 @EnableAutoDataSourceProxy (since 1.1.0) 注解，注解参数可选择 jdk 代理或者 cglib 代理。
 * 依赖 seata-all 时，也可以手动使用 DatasourceProxy 来包装 DataSource。
 *
 * @Desc: 新增DataSourceConfiguration.java，Seata的RM通过DataSourceProxy才能在业务代码的事务提交时，
 *        通过这个切入点，与TC进行通信互换，记录undo_log等
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:41
 */
@Configuration
public class DataSourceConfiguration {


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean("druidDataSource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSource(DataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

    /**
     * 把spring本地事务数据源设置为seata代理数据源，防止本地事务失效
     * @param dataSourceProxy
     * @return
     */
    @Bean("txManager")
    public DataSourceTransactionManager txManager(DataSourceProxy dataSourceProxy){
        return new DataSourceTransactionManager(dataSourceProxy);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);
        return factoryBean.getObject();
    }
}
