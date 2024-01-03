package com.deng.study.shardingsphere.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @Desc: 自定义分片策略：因为有些分片值不是整数，就没有办法直接用配置文件中的取模运算
 * @Date: 2024/1/3 09:27
 * @Auther: dengyanliang
 */
public class MySharding implements PreciseShardingAlgorithm<String> {

    /**
     *
     * @param collection 可用的分片表
     * @param preciseShardingValue 当前分片值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String id = preciseShardingValue.getValue();
        // 根据分片值的hashCode跟每个库中的表数量进行取模运算
        int mode = id.hashCode() % collection.size();
        // hashCode有可能为负数，所以这里取绝对值
        mode = Math.abs(mode);

        // 把集合转化为数组
        String[] array = collection.toArray(new String[0]);
        System.out.println("doSharding  array-------->" + array[0] + "," + array[1]);
        System.out.println("mode---------->" + mode);

        return array[mode];
    }
}
