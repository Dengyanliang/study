package com.deng.seata.order.tx.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//import io.seata.rm.datasource.DataSourceProxy;

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


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    // Seata是通过代理数据源实现事务分支的，所以需要配置io.seata.rm.datasource.DataSourceProxy的Bean，
    // 且是@Primary默认的数据源，否则事务不会回滚，无法实现分布式事务
    // 这两种模式分支事务会提交并在undo_log中写入数据库，如果需要回滚则做相反操作。
    @Primary
    @Bean("dataSourceProxy")
    public DataSource dataSourceProxy(DataSource druidDataSource){

        // AT模式
//        return new DataSourceProxy(druidDataSource);

        // XA模式 keypoint 需要seata1.4.1 及其以上的版本才支持
        return new DataSourceProxyXA(druidDataSource);
    }
}
