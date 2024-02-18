package com.deng.study.shardingsphere.sharding;


import com.deng.common.constant.MagicNumber;
import com.deng.common.util.StringCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.zip.CRC32;


/**
 * PreciseShardingAlgorithm 精准算法（sql语句中有=、in）此必须实现
 */
@Slf4j
public final class TablePreciseShardingAlgorithm8 implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        CRC32 crc32 = new CRC32();
        crc32.update(shardingValue.getValue().getBytes(StandardCharsets.UTF_8));
        String value = String.valueOf(crc32.getValue());
        for (String tableName : availableTargetNames) {
            // keypoint 分库和分表如果使用的key是同一个，那么分片算法一定不能相同，否则有些表根本存不进去数据
//            if (tableName.endsWith("_" + StringCommonUtils.tail4(String.valueOf(Math.abs(shardingValue.getValue().hashCode()) % MagicNumber.int_8)))) {
            if (tableName.endsWith("_" + StringCommonUtils.tail4(String.valueOf(Long.parseLong(StringCommonUtils.tail4(value)) % MagicNumber.int_8)))) {
                log.info("shardingValue：{}，选择到的表是：{},", shardingValue.getValue(),tableName);
                return tableName;
            }
        }
        throw new UnsupportedOperationException();
    }
}