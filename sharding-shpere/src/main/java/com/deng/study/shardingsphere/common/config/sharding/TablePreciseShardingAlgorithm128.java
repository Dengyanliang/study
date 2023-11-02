package com.deng.study.shardingsphere.common.config.sharding;


import com.deng.common.util.StringCommonUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.zip.CRC32;


/**
 * @author silei
 */
public final class TablePreciseShardingAlgorithm128 implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        CRC32 crc32 = new CRC32();
        crc32.update(shardingValue.getValue().getBytes(StandardCharsets.UTF_8));
        String i = String.valueOf(crc32.getValue());
        for (String each : availableTargetNames) {
            if (each.endsWith("_" + StringCommonUtils.tail4((Long.parseLong(StringCommonUtils.tail4(i)) % 128) + ""))) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}