package com.deng.study.shardingsphere.sharding;

import com.deng.common.constant.MagicNumber;
import com.deng.common.util.StringCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 *
 */
@Slf4j
public class DataBaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        for (String dbName : availableTargetNames) {
            if (dbName.endsWith("_" + StringCommonUtils.tail2(String.valueOf(Math.abs(shardingValue.getValue().hashCode()) % MagicNumber.int_4)))) {
                log.info("shardingValue：{}，选择到的数据库是：{},", shardingValue.getValue(), dbName);
                return dbName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
