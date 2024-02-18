package com.deng.study.shardingsphere.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;


/**
 * RangeShardingAlgorithm 范围算法（between and）
 */
public final class TableRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        Range<String> valueRange = rangeShardingValue.getValueRange();
        /*for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith(i % size + "")) {
                    collect.add(each);
                }
            }
        }*/
        return collect;
    }
}