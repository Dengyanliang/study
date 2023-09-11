//package com.deng.seata.order.tx.config;
//
//import io.seata.rm.datasource.DataSourceProxy;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 依赖 seata-spring-boot-starter 时，自动代理数据源，无需额外处理。
// * 依赖 seata-all 时，使用 @EnableAutoDataSourceProxy (since 1.1.0) 注解，注解参数可选择 jdk 代理或者 cglib 代理。
// * 依赖 seata-all 时，也可以手动使用 DatasourceProxy 来包装 DataSource。
// *
// * @Desc: 新增DataSourceConfiguration.java，Seata的RM通过DataSourceProxy才能在业务代码的事务提交时，
// *        通过这个切入点，与TC进行通信互换，记录undo_log等
// * @Auther: dengyanliang
// * @Date: 2021/9/26 07:41
// */
//@Configuration
//@MapperScan(basePackages = "com.deng.seata.order.tx.dao.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
//public class DataSourceConfiguration {
//
//
//    @ConfigurationProperties(prefix = "spring.datasource.ds")
//    @Bean("dataSourceProperties")
//    public DataSourceProperties dataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//    @Bean("druidDataSource")
//    public DataSource druidDataSource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties){
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean
//    public DataSource dataSourceProxy(@Qualifier("druidDataSource") DataSource druidDataSource){
//        return new DataSourceProxy(druidDataSource);
//    }
//
//    /**
//     * 把spring本地事务数据源设置为seata代理数据源，防止本地事务失效
//     * @param dataSourceProxy
//     * @return
//     */
//    @Bean("txManager")
//    public DataSourceTransactionManager txManager(DataSource dataSourceProxy){
//        return new DataSourceTransactionManager(dataSourceProxy);
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDataSource") DataSource druidDataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(druidDataSource);
//        return factoryBean.getObject();
//    }
//}
