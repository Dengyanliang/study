package com.deng.seata.saga.order.config;

import com.deng.seata.saga.order.utils.ResourceUtil;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Desc: 状态机的配置
 * @Date: 2024/5/11 14:45
 * @Auther: dengyanliang
 */
@Configuration
public class StateMachineConfiguration {


    @Bean
    public ProcessCtrlStateMachineEngine stateMachineEngine(DbStateMachineConfig dbStateMachineConfig){
        ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
        stateMachineEngine.setStateMachineConfig(dbStateMachineConfig);
        return stateMachineEngine;
    }


    @Bean
    public StateMachineEngineHolder stateMachineEngineHolder(StateMachineEngine stateMachineEngine){
        StateMachineEngineHolder stateMachineEngineHolder = new StateMachineEngineHolder();
        stateMachineEngineHolder.setStateMachineEngine(stateMachineEngine);
        return stateMachineEngineHolder;
    }


    @Bean
    public DbStateMachineConfig dbStateMachineConfig(@Qualifier("druidDataSource2") DataSource druidDataSource2) {
        DbStateMachineConfig dbStateMachineConfig = new DbStateMachineConfig();
        dbStateMachineConfig.setDataSource(druidDataSource2);
//        dbStateMachineConfig.setResources(ResourceUtil.getResources("classpath*:statelang/*.json"));
        dbStateMachineConfig.setResources(new String[]{"classpath*:statelang/*.json"});
        dbStateMachineConfig.setEnableAsync(true);
        dbStateMachineConfig.setApplicationId("dtx-seata-saga-order");
        dbStateMachineConfig.setTxServiceGroup("default_tx_group");
//        dbStateMachineConfig.setThreadPoolExecutor();

        return dbStateMachineConfig;
    }
}
