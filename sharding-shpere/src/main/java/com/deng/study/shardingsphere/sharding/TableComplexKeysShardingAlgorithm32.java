//package com.deng.study.shardingsphere.sharding;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.CRC32;
//
///**
// * @Desc:这里的符合算法不对
// * @Date: 2024/1/31 18:23
// * @Auther: dengyanliang
// */
//@Slf4j
//public class TableComplexKeysShardingAlgorithm32 implements ComplexKeysShardingAlgorithm<String> {
//
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTableNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
//        Map<String, Collection<String>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
////        Collection<String> tradeNos = complexKeysShardingValue.getColumnNameAndShardingValuesMap().getOrDefault(SHARDING_KEY_TRADE_NO, new ArrayList<>());
////
////        List<String> ids = Lists.newArrayList();
////        ids.add(payNos);
////        ids.add(tradeNos);
//
////        CRC32 crc32 = new CRC32();
////        crc32.update(complexKeysShardingValue.getBytes(StandardCharsets.UTF_8));
////        String value = String.valueOf(crc32.getValue());
////        for (String tableName : availableTableNames) {
////            if(tableName.endsWith("_" + StringCommonUtils.tail4(String.valueOf(Long.parseLong(StringCommonUtils.tail4(value)) % MagicNumber.int_32)))){
////                log.info("pay_no对应的shardingValue：{}，选择到的表是：{},", complexKeysShardingValue, tableName);
////                return Lists.newArrayList(tableName);
////            }
////        }
//
//        List<String> resultTableList = new ArrayList<>();
//
//        if(columnNameAndShardingValuesMap.containsKey(SHARDING_KEY_PAY_NO)){
//            String shardingValue = columnNameAndShardingValuesMap.get(SHARDING_KEY_PAY_NO).iterator().next();
//            CRC32 crc32 = new CRC32();
//            crc32.update(shardingValue.getBytes(StandardCharsets.UTF_8));
//            String value = String.valueOf(crc32.getValue());
//            for (String tableName : availableTableNames) {
//                if(tableName.endsWith("_" + StringCommonUtils.tail4(String.valueOf(Long.parseLong(StringCommonUtils.tail4(value)) % MagicNumber.int_32)))){
//                    log.info("pay_no对应的shardingValue：{}，选择到的表是：{},", shardingValue, tableName);
//                    resultTableList.add(tableName);
//                }
//            }
//        }
//
//        if(columnNameAndShardingValuesMap.containsKey(SHARDING_KEY_TRADE_NO)){
//            String shardingValue = columnNameAndShardingValuesMap.get(SHARDING_KEY_TRADE_NO).iterator().next();
//            CRC32 crc32 = new CRC32();
//            crc32.update(shardingValue.getBytes(StandardCharsets.UTF_8));
//            String value = String.valueOf(crc32.getValue());
//            for (String tableName : availableTableNames) {
//                if(tableName.endsWith("_" + StringCommonUtils.tail4(String.valueOf(Long.parseLong(StringCommonUtils.tail4(value)) % MagicNumber.int_32)))){
//                    log.info("trade_no对应的shardingValue：{}，选择到的表是：{},", shardingValue, tableName);
////                    return Lists.newArrayList(tableName);
//                    resultTableList.add(tableName);
//                }
//            }
//        }
//
//        return resultTableList;
//    }
//}
//
