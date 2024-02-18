package com.deng.study.shardingsphere.sharding;


import com.deng.common.util.StringCommonUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.zip.CRC32;


/**
 * @author dengyanliang
 */
public final class TablePreciseShardingAlgorithm128 implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        // 如果直接取余，离散度不好
        CRC32 crc32 = new CRC32();
        crc32.update(shardingValue.getValue().getBytes(StandardCharsets.UTF_8));
        String i = String.valueOf(crc32.getValue());
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith("_" + StringCommonUtils.tail4((Long.parseLong(StringCommonUtils.tail4(i)) % 128) + ""))) {
                return tableName;
            }
        }
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        String shardingValue = "123";
        // 这种方式定位到的是  0018 表
        CRC32 crc32 = new CRC32();
        crc32.update(shardingValue.getBytes(StandardCharsets.UTF_8));
        String i = String.valueOf(crc32.getValue()); // 0018

        // 这种方式定位到的是  0123 表
//        String i = shardingValue; // 0123
        String s = StringCommonUtils.tail4((Long.parseLong(StringCommonUtils.tail4(i)) % 128)+ "");
        System.out.println(s);
    }
}