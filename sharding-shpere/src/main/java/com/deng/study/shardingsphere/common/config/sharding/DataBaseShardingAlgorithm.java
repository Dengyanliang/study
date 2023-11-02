package com.deng.study.shardingsphere.common.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author silei
 */
public class DataBaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith("_" + Math.abs(shardingValue.getValue().hashCode()) % 8)) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

//    @Override
//    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
//        CRC32 crc32 = new CRC32();
//        crc32.update(shardingValue.getValue().getBytes(StandardCharsets.UTF_8));
//        String i = String.valueOf(crc32.getValue());
//        for (String each : availableTargetNames) {
//            if (each.endsWith("_" + (Long.parseLong(StringCommonUtils.tail4(i)) % 8) + "")) {
//                return each;
//            }
//        }
//        throw new UnsupportedOperationException();
//    }

}
