package com.deng.study.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import java.net.InetSocketAddress;
import java.util.List;


import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;

/**
 * @Desc: Canal监听数据库，并同步到Redis
 * @Auther: dengyanliang
 * @Date: 2023/2/27 19:55
 */
public class RedisCanalTest {

    public static void main(String args[]) {
        // 创建链接，这里的端口号随机填写即可
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(RedisUtil.REDIS_HOST_IP,
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        System.out.println("----开始监听mysql变化-----");
        try {
            connector.connect();
//            connector.subscribe(".*\\..*"); // 这是监听所有的库的所有表，不建议这么做
            connector.subscribe("deng.pay_order");
            connector.rollback();
            int totalEmptyCount = 120; // 120秒就结束了
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                    redisDelete(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                    redisInsert(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------修改之前--------");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------修改之后--------");
                    printColumn(rowData.getAfterColumnsList());

                    redisUpdate(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void redisUpdate(List<Column> columns) {
        if(CollectionUtils.isEmpty(columns)){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        for (Column column : columns) {
            jsonObject.put(column.getName(),column.getValue());
        }
        try(Jedis jedis = RedisUtil.getJedis()) {
            jedis.set(columns.get(0).getValue(),jsonObject.toJSONString());
            System.out.println("----update after：" + jedis.get(columns.get(0).getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void redisInsert(List<Column> columns) {
        if(CollectionUtils.isEmpty(columns)){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        for (Column column : columns) {
            jsonObject.put(column.getName(),column.getValue());
        }
        try(Jedis jedis = RedisUtil.getJedis()) {
            jedis.set(columns.get(0).getValue(),jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void redisDelete(List<Column> columns) {
        if(CollectionUtils.isEmpty(columns)){
            return;
        }
        try(Jedis jedis = RedisUtil.getJedis()) {
            jedis.del(columns.get(0).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }


}
