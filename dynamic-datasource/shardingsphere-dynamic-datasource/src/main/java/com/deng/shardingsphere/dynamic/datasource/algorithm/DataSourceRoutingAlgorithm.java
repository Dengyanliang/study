package com.deng.shardingsphere.dynamic.datasource.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/10/18 20:22
 */
public class DataSourceRoutingAlgorithm implements HintShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<String> hintShardingValue) {
        Set<String> result = new HashSet<>();
        for (String value : hintShardingValue.getValues()) {
            if(availableTargetNames.contains(value)){
                result.add(value);
            }
        }
        return result;
    }
}
