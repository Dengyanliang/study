package com.deng.seata.tcc.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
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
 *
 * @Desc: 新增DataSourceConfiguration.java，Seata的RM通过DataSourceProxy才能在业务代码的事务提交时，
 *        通过这个切入点，与TC进行通信互换，记录undo_log等
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

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource(){
//        return new DruidDataSource();
//    }

    // Seata是通过代理数据源实现事务分支的，所以需要配置io.seata.rm.datasource.DataSourceProxy的Bean，
    // 且是@Primary默认的数据源，否则事务不会回滚，无法实现分布式事务
//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSource(@Qualifier("druidDataSource1")  DruidDataSource druidDataSource1){
//        return new DataSourceProxy(druidDataSource1);
//    }
}
