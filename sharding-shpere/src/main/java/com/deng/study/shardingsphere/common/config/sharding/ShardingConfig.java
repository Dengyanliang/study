package com.deng.study.shardingsphere.common.config.sharding;

import com.deng.common.constant.MagicNumber;
import com.deng.common.util.StringCommonUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(value = ShardingProperties.class)
public class ShardingConfig {

    @Autowired
    private ShardingProperties shardingProperties;

    @Primary
    @Bean("dataSource")
    DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // order_no 分片键
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());

        //单库单表
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("my_order", "my_order", "deng"));

        shardingRuleConfig.setDefaultDataSourceName("deng");

        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.TRUE.toString());

        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }

    private TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order",buildTable("t_order"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_no", new DataBaseShardingAlgorithm()));
        /**
         * 对应StandardShardingStrategy。提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持。
         * StandardShardingStrategy只支持单分片键，提供PreciseShardingAlgorithm和RangeShardingAlgorithm两个分片算法。
         * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
         * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理。
         */
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_no", new TablePreciseShardingAlgorithm4(), new TableRangeShardingAlgorithm()));
        return result;
    }

    /**
     * 分表策略
     */
    private String buildTable(String tableName) {
        StringBuilder builder = new StringBuilder();
        for (int b = 0; b < MagicNumber.int_2; b++) {
            for (int i = 0; i < MagicNumber.int_4; i++) {
                builder.append("order_new_db").append("_").append(b).append(".").append(tableName).append("_").append(StringCommonUtils.tail4(i + "")).append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }


    /**
     * 不分表的表 映射规则
     *
     * @param logicTable 逻辑表名
     * @param tableName  物理表名
     * @param dataSource 数据源（库名）
     * @return TableRuleConfiguration
     */
    private TableRuleConfiguration buildActualTable(String logicTable, String tableName, String dataSource) {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration(logicTable,dataSource + "." + tableName);
        return orderTableRuleConfig;
    }
    /**
     * 数据源配置
     *
     * @return
     */
    private Map<String, DataSource> createDataSourceMap() {
        Map<String,DataSource> map = new HashMap<>();

        HikariDataSource dataSource = createDataSource(shardingProperties.getUrl(),shardingProperties.getUsername(),shardingProperties.getPassword(), "deng");
        HikariDataSource dataSource0 = createDataSource(shardingProperties.getUrl0(),shardingProperties.getUsername0(),shardingProperties.getPassword0(),"order_new_db_0");
        HikariDataSource dataSource1 = createDataSource(shardingProperties.getUrl1(),shardingProperties.getUsername1(),shardingProperties.getPassword1(),"order_new_db_1");

        map.put("deng",dataSource);
        map.put("order_new_db_0",dataSource0);
        map.put("order_new_db_1",dataSource1);

        return map;
    }

    private HikariDataSource createDataSource(String url,String username,String password,String poolName){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMinimumIdle(10);  // TODO 一般是怎么配置的？？
        hikariConfig.setMaximumPoolSize(60);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setPoolName(poolName);
        return new HikariDataSource(hikariConfig);
    }

}
