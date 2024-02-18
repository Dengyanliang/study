package com.deng.study.shardingsphere.sharding;

import com.deng.common.constant.MagicNumber;
import com.deng.common.util.StringCommonUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
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
//        properties.setProperty("sql.show", Boolean.TRUE.toString());

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
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_no", new TablePreciseShardingAlgorithm8(), new TableRangeShardingAlgorithm()));
        return result;
    }

    /**
     * 分表策略
     */
    private String buildTable(String tableName) {
        StringBuilder builder = new StringBuilder();
        for (int dbIndex = 0; dbIndex < MagicNumber.int_4; dbIndex++) {
            for (int tableIndex = 0; tableIndex < MagicNumber.int_8; tableIndex++) {
                builder.append("order_new_db").append("_").append(StringCommonUtils.tail2(String.valueOf(dbIndex))).append(".").
                        append(tableName).append("_").append(StringCommonUtils.tail4(String.valueOf(tableIndex))).append(",");
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
        HikariDataSource dataSource0 = createDataSource(shardingProperties.getUrl0(),shardingProperties.getUsername0(),shardingProperties.getPassword0(),"order_new_db_00");
        HikariDataSource dataSource1 = createDataSource(shardingProperties.getUrl1(),shardingProperties.getUsername1(),shardingProperties.getPassword1(),"order_new_db_01");
        HikariDataSource dataSource2 = createDataSource(shardingProperties.getUrl2(),shardingProperties.getUsername2(),shardingProperties.getPassword2(),"order_new_db_02");
        HikariDataSource dataSource3 = createDataSource(shardingProperties.getUrl3(),shardingProperties.getUsername3(),shardingProperties.getPassword3(),"order_new_db_03");
        HikariDataSource dataSource4 = createDataSource(shardingProperties.getUrl4(),shardingProperties.getUsername4(),shardingProperties.getPassword4(),"order_new_db_04");
        HikariDataSource dataSource5 = createDataSource(shardingProperties.getUrl5(),shardingProperties.getUsername5(),shardingProperties.getPassword5(),"order_new_db_05");
        HikariDataSource dataSource6 = createDataSource(shardingProperties.getUrl6(),shardingProperties.getUsername6(),shardingProperties.getPassword6(),"order_new_db_06");
        HikariDataSource dataSource7 = createDataSource(shardingProperties.getUrl7(),shardingProperties.getUsername7(),shardingProperties.getPassword7(),"order_new_db_07");
        HikariDataSource dataSource8 = createDataSource(shardingProperties.getUrl8(),shardingProperties.getUsername8(),shardingProperties.getPassword8(),"order_new_db_08");
        HikariDataSource dataSource9 = createDataSource(shardingProperties.getUrl9(),shardingProperties.getUsername9(),shardingProperties.getPassword9(),"order_new_db_09");
        HikariDataSource dataSource10 = createDataSource(shardingProperties.getUrl10(),shardingProperties.getUsername10(),shardingProperties.getPassword10(),"order_new_db_10");
        HikariDataSource dataSource11 = createDataSource(shardingProperties.getUrl11(),shardingProperties.getUsername11(),shardingProperties.getPassword11(),"order_new_db_11");
        HikariDataSource dataSource12 = createDataSource(shardingProperties.getUrl12(),shardingProperties.getUsername12(),shardingProperties.getPassword12(),"order_new_db_12");
        HikariDataSource dataSource13 = createDataSource(shardingProperties.getUrl13(),shardingProperties.getUsername13(),shardingProperties.getPassword13(),"order_new_db_13");
        HikariDataSource dataSource14 = createDataSource(shardingProperties.getUrl14(),shardingProperties.getUsername14(),shardingProperties.getPassword14(),"order_new_db_14");
        HikariDataSource dataSource15 = createDataSource(shardingProperties.getUrl15(),shardingProperties.getUsername15(),shardingProperties.getPassword15(),"order_new_db_15");

        map.put("deng",dataSource);
        map.put("order_new_db_00",dataSource0);
        map.put("order_new_db_01",dataSource1);
        map.put("order_new_db_02",dataSource2);
        map.put("order_new_db_03",dataSource3);
        map.put("order_new_db_04",dataSource4);
        map.put("order_new_db_05",dataSource5);
        map.put("order_new_db_06",dataSource6);
        map.put("order_new_db_07",dataSource7);
        map.put("order_new_db_08",dataSource8);
        map.put("order_new_db_09",dataSource9);
        map.put("order_new_db_10",dataSource10);
        map.put("order_new_db_11",dataSource11);
        map.put("order_new_db_12",dataSource12);
        map.put("order_new_db_13",dataSource13);
        map.put("order_new_db_14",dataSource14);
        map.put("order_new_db_15",dataSource15);

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
