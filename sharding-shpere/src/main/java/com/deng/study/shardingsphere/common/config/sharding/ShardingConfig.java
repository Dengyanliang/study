package com.deng.study.shardingsphere.common.config.sharding;

import com.deng.common.constant.MagicNumber;
import com.deng.common.util.StringCommonUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: silei
 * @Date: 2021/6/8
 * @desc:
 */
//@Configuration
//@EnableConfigurationProperties(value = ShardingProperties.class)
public class ShardingConfig {

//    @Autowired
    private ShardingProperties shardingProperties;

//    @Primary
//    @Bean("dataSource")
    DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getAccountTransactionTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getAccountBookTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getAccountFlowTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getAccountPointRecordTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getAccountVerifyFlowTableRuleConfiguration());
        //单库单表
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("account_error_transaction", "account_error_transaction", "fin_point_core"));
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("transaction_task_log", "transaction_task_log", "fin_point_core"));
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("account_wait_flow", "account_wait_flow", "fin_point_core"));
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("account_flow_hotspot","account_flow_hotspot","fin_point_core"));
        shardingRuleConfig.getTableRuleConfigs().add(buildActualTable("account_hotspot_task","account_hotspot_task","fin_point_core"));



        shardingRuleConfig.setDefaultDataSourceName("fin_point_core_0");

        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.TRUE.toString());

        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }

    private TableRuleConfiguration getAccountVerifyFlowTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account_verify_flow",buildTable("account_verify_flow_"));
//        result.setLogicTable("account_verify_flow");
//        result.setActualDataNodes(buildTable("account_verify_flow_"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new DataBaseShardingAlgorithm()));
        /**
         * 对应StandardShardingStrategy。提供对SQL语句中的=, IN和BETWEEN AND的分片操作支持。
         * StandardShardingStrategy只支持单分片键，提供PreciseShardingAlgorithm和RangeShardingAlgorithm两个分片算法。
         * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
         * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理。
         */
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new TablePreciseShardingAlgorithm128(), new TableRangeShardingAlgorithm()));
        return result;
    }

    private TableRuleConfiguration getAccountPointRecordTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account_point_record",buildTable("account_point_record_"));
//        result.setLogicTable("account_point_record");
//        result.setActualDataNodes(buildTable("account_point_record_"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new DataBaseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new TablePreciseShardingAlgorithm128(), new TableRangeShardingAlgorithm()));
        return result;
    }

    private TableRuleConfiguration getAccountFlowTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account_flow",buildTable("account_flow_"));
//        result.setLogicTable("account_flow");
//        result.setActualDataNodes(buildTable("account_flow_"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new DataBaseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new TablePreciseShardingAlgorithm128(), new TableRangeShardingAlgorithm()));
        return result;
    }

    private TableRuleConfiguration getAccountBookTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account_book",buildAccountBookTable("account_book_"));
//        result.setLogicTable("account_book");
//        result.setActualDataNodes(buildAccountBookTable("account_book_"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new DataBaseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("account_no", new TablePreciseShardingAlgorithm16(), new TableRangeShardingAlgorithm()));
        return result;
    }

    private String buildAccountBookTable(String tableName) {
        StringBuilder builder = new StringBuilder();
        for (int b = 0; b < MagicNumber.int_8; b++) {
            for (int i = 0; i < MagicNumber.int_16; i++) {
                builder.append("fin_point_core_").append(b).append(".").append(tableName).append(StringCommonUtils.tail3(i + "")).append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }

    private TableRuleConfiguration getAccountTransactionTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("account_transaction",buildTable("account_transaction_"));
//        result.setLogicTable("account_transaction");
//        result.setActualDataNodes(buildTable("account_transaction_"));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("trade_no", new DataBaseShardingAlgorithm()));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("trade_no", new TablePreciseShardingAlgorithm128(), new TableRangeShardingAlgorithm()));
        return result;
    }

    /**
     * bcs_bill 分表策略
     */
    private String buildTable(String tableName) {
        StringBuilder builder = new StringBuilder();
        for (int b = 0; b < MagicNumber.int_8; b++) {
            for (int i = 0; i < MagicNumber.int_128; i++) {
                builder.append("fin_point_core_").append(b).append(".").append(tableName).append(StringCommonUtils.tail4(i + "")).append(",");
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
//        orderTableRuleConfig.setLogicTable(logicTable);
//        orderTableRuleConfig.setActualDataNodes(dataSource + "." + tableName);
        return orderTableRuleConfig;
    }
    /**
     * 数据源配置
     *
     * @return
     */
    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();

        /**
         * fin_point_core
         */
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig.setJdbcUrl(shardingProperties.getUrl());
        hikariConfig.setUsername(shardingProperties.getUsername());
        hikariConfig.setPassword(shardingProperties.getPassword());
        hikariConfig.setMaximumPoolSize(60);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setPoolName("fin_point_core");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        /**
         * fin_point_core_0
         */
        HikariConfig hikariConfig0 = new HikariConfig();
        hikariConfig0.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig0.setJdbcUrl(shardingProperties.getUrl0());
        hikariConfig0.setUsername(shardingProperties.getUsername0());
        hikariConfig0.setPassword(shardingProperties.getPassword0());
        hikariConfig0.setMaximumPoolSize(60);
        hikariConfig0.setMaxLifetime(1800000);
        hikariConfig0.setConnectionTimeout(30000);
        hikariConfig0.setIdleTimeout(600000);
        hikariConfig0.setMinimumIdle(10);
        hikariConfig0.setPoolName("fin_point_core_0");
        HikariDataSource dataSource0 = new HikariDataSource(hikariConfig0);

        /**
         * fin_point_core_1
         * */
        HikariConfig hikariConfig1 = new HikariConfig();
        hikariConfig1.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig1.setJdbcUrl(shardingProperties.getUrl1());
        hikariConfig1.setUsername(shardingProperties.getUsername1());
        hikariConfig1.setPassword(shardingProperties.getPassword1());
        hikariConfig1.setMaximumPoolSize(60);
        hikariConfig1.setMaxLifetime(1800000);
        hikariConfig1.setConnectionTimeout(30000);
        hikariConfig1.setIdleTimeout(600000);
        hikariConfig1.setMinimumIdle(10);
        hikariConfig1.setPoolName("fin_point_core_1");
        HikariDataSource dataSource1 = new HikariDataSource(hikariConfig1);

        /**
         * fin_point_core_2
         */
        HikariConfig hikariConfig2 = new HikariConfig();
        hikariConfig2.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig2.setJdbcUrl(shardingProperties.getUrl2());
        hikariConfig2.setUsername(shardingProperties.getUsername2());
        hikariConfig2.setPassword(shardingProperties.getPassword2());
        hikariConfig2.setMaximumPoolSize(60);
        hikariConfig2.setMaxLifetime(1800000);
        hikariConfig2.setConnectionTimeout(30000);
        hikariConfig2.setIdleTimeout(600000);
        hikariConfig2.setMinimumIdle(10);
        hikariConfig2.setPoolName("fin_point_core_2");
        HikariDataSource dataSource2 = new HikariDataSource(hikariConfig2);

        /**
         * fin_point_core_3
         */
        HikariConfig hikariConfig3 = new HikariConfig();
        hikariConfig3.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig3.setJdbcUrl(shardingProperties.getUrl3());
        hikariConfig3.setUsername(shardingProperties.getUsername3());
        hikariConfig3.setPassword(shardingProperties.getPassword3());
        hikariConfig3.setMaximumPoolSize(60);
        hikariConfig3.setMaxLifetime(1800000);
        hikariConfig3.setConnectionTimeout(30000);
        hikariConfig3.setIdleTimeout(600000);
        hikariConfig3.setMinimumIdle(10);
        hikariConfig3.setPoolName("fin_point_core_3");
        HikariDataSource dataSource3 = new HikariDataSource(hikariConfig3);

        /**
         * fin_point_core_4
         */
        HikariConfig hikariConfig4 = new HikariConfig();
        hikariConfig4.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig4.setJdbcUrl(shardingProperties.getUrl4());
        hikariConfig4.setUsername(shardingProperties.getUsername4());
        hikariConfig4.setPassword(shardingProperties.getPassword4());
        hikariConfig4.setMaximumPoolSize(60);
        hikariConfig4.setMaxLifetime(1800000);
        hikariConfig4.setConnectionTimeout(30000);
        hikariConfig4.setIdleTimeout(600000);
        hikariConfig4.setMinimumIdle(10);
        hikariConfig4.setPoolName("fin_point_core_4");
        HikariDataSource dataSource4 = new HikariDataSource(hikariConfig4);

        /**
         * fin_point_core_5
         */
        HikariConfig hikariConfig5 = new HikariConfig();
        hikariConfig5.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig5.setJdbcUrl(shardingProperties.getUrl5());
        hikariConfig5.setUsername(shardingProperties.getUsername5());
        hikariConfig5.setPassword(shardingProperties.getPassword5());
        hikariConfig5.setMaximumPoolSize(60);
        hikariConfig5.setMaxLifetime(1800000);
        hikariConfig5.setConnectionTimeout(30000);
        hikariConfig5.setIdleTimeout(600000);
        hikariConfig5.setMinimumIdle(10);
        hikariConfig5.setPoolName("fin_point_core_5");
        HikariDataSource dataSource5 = new HikariDataSource(hikariConfig5);

        /**
         * fin_point_core_6
         */
        HikariConfig hikariConfig6 = new HikariConfig();
        hikariConfig6.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig6.setJdbcUrl(shardingProperties.getUrl6());
        hikariConfig6.setUsername(shardingProperties.getUsername6());
        hikariConfig6.setPassword(shardingProperties.getPassword6());
        hikariConfig6.setMaximumPoolSize(60);
        hikariConfig6.setMaxLifetime(1800000);
        hikariConfig6.setConnectionTimeout(30000);
        hikariConfig6.setIdleTimeout(600000);
        hikariConfig6.setMinimumIdle(10);
        hikariConfig6.setPoolName("fin_point_core_6");
        HikariDataSource dataSource6 = new HikariDataSource(hikariConfig6);

        /**
         * fin_point_core_7
         */
        HikariConfig hikariConfig7 = new HikariConfig();
        hikariConfig7.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        hikariConfig7.setJdbcUrl(shardingProperties.getUrl7());
        hikariConfig7.setUsername(shardingProperties.getUsername7());
        hikariConfig7.setPassword(shardingProperties.getPassword7());
        hikariConfig7.setMaximumPoolSize(60);
        hikariConfig7.setMaxLifetime(1800000);
        hikariConfig7.setConnectionTimeout(30000);
        hikariConfig7.setIdleTimeout(600000);
        hikariConfig7.setMinimumIdle(10);
        hikariConfig7.setPoolName("fin_point_core_7");
        HikariDataSource dataSource7 = new HikariDataSource(hikariConfig7);

        result.put("fin_point_core", dataSource);
        result.put("fin_point_core_0", dataSource0);
        result.put("fin_point_core_1", dataSource1);
        result.put("fin_point_core_2", dataSource2);
        result.put("fin_point_core_3", dataSource3);
        result.put("fin_point_core_4", dataSource4);
        result.put("fin_point_core_5", dataSource5);
        result.put("fin_point_core_6", dataSource6);
        result.put("fin_point_core_7", dataSource7);
        return result;
    }

}
