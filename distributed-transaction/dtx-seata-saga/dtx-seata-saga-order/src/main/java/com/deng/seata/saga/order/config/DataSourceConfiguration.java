package com.deng.seata.saga.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * 依赖 seata-spring-boot-starter 时，自动代理数据源，无需额外处理，默认是AT模式
 * 依赖 seata-all 时，使用 @EnableAutoDataSourceProxy (since 1.1.0) 注解，注解参数可选择 jdk 代理或者 cglib 代理。
 * 依赖 seata-all 时，也可以手动使用 DatasourceProxy 来包装 DataSource。
 * 新增DataSourceConfiguration.java，Seata的RM通过DataSourceProxy才能在业务代码的事务提交时，
 * 通过这个切入点，与TC进行通信互换，记录undo_log等
 *
 * @Desc: 数据源配置
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:41
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean("druidDataSource1")
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource druidDataSource1(){
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource druidDataSource2(){
        return new DruidDataSource();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("druidDataSource1")  DruidDataSource druidDataSource1) {
        return new DataSourceTransactionManager(druidDataSource1);
    }

}
