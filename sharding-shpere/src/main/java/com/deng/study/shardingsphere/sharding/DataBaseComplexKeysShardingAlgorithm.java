//package com.deng.study.shardingsphere.sharding;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @Desc: 这里的符合算法不对
// * @Date: 2024/1/31 17:57
// * @Auther: dengyanliang
// */
//@Slf4j
//public class DataBaseComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
//
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availablDbNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
//
//        List<String> resultDbList = new ArrayList<>();
//
//        Map<String, Collection<String>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
//        if(columnNameAndShardingValuesMap.containsKey(SHARDING_KEY_PAY_NO)){
//            String shardingValue = columnNameAndShardingValuesMap.get(SHARDING_KEY_PAY_NO).iterator().next();
//            for (String dbName : availablDbNames) {
//                // 取hash的绝对值，防止负数产生
//                if (dbName.endsWith("_" + StringCommonUtils.tail2(String.valueOf(Math.abs(shardingValue.hashCode()) % MagicNumber.int_2)))) {
//                    log.info("pay_no对应的shardingValue：{}，选择到的数据库是：{},", shardingValue, dbName);
////                    return Lists.newArrayList(dbName);
//                    resultDbList.add(dbName);
//                }
//            }
//        }
//
//        if(columnNameAndShardingValuesMap.containsKey(SHARDING_KEY_TRADE_NO)){
//            String shardingValue = columnNameAndShardingValuesMap.get(SHARDING_KEY_TRADE_NO).iterator().next();
//            for (String dbName : availablDbNames) {
//                // 取hash的绝对值，防止负数产生
//                if (dbName.endsWith("_" + StringCommonUtils.tail2(String.valueOf(Math.abs(shardingValue.hashCode()) % MagicNumber.int_2)))) {
//                    log.info("trade_no对应的shardingValue：{}，选择到的数据库是：{},", shardingValue, dbName);
////                    return Lists.newArrayList(dbName);
//                    resultDbList.add(dbName);
//                }
//            }
//        }
//        return resultDbList;
//
////        throw new UnsupportedOperationException();
//    }
//
//}
