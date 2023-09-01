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
import com.deng.study.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

/**
 * @Desc: Canal监听数据库，并同步到Redis
 * @Auther: dengyanliang
 * @Date: 2023/2/27 19:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CanalTest {

    @Test
    public void testCanal(){
        // 1、创建连接，这里的端口号随机填写即可
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        System.out.println("----开始监听mysql变化-----");
        try {
            // 2、建立Canal连接
            connector.connect();
//            connector.subscribe(".*\\..*"); // 这是监听所有的库的所有表，不建议这么做
//             3、订阅数据库
            connector.subscribe("course_db.course");
            connector.rollback();
            int totalEmptyCount = 120; // 120秒就结束x`了
            while (emptyCount < totalEmptyCount) {
                // 4、获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    System.out.println("没有获取到数据。。。");
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    try {
                        // 处理数据，打印、写入到缓存、写入到数据库
                        handleEntry(message.getEntries());
                        connector.ack(batchId); // 提交确认
                    } catch (Exception e) {
//                        connector.rollback(batchId); // 处理失败, 回滚数据
                    }
                }
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private void handleEntry(List<Entry> entrys) throws Exception{
        for (Entry entry : entrys) {
            // 如果不是数据变化，则过滤掉
            if (entry.getEntryType() != EntryType.ROWDATA) {
                continue;
            }

            RowChange rowChage = null;
            try {
                // 获取具体的数据
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            // 获取事件类型
            EventType eventType = rowChage.getEventType();
            String tableName = entry.getHeader().getTableName();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), tableName,
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                    deleteRedis(rowData.getBeforeColumnsList());
//                    deleteDb(rowData.getBeforeColumnsList(),tableName);
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                    insertRedis(rowData.getAfterColumnsList());
//                    insertDb(rowData.getAfterColumnsList(),tableName);
                } else if (eventType == EventType.UPDATE){
                    System.out.println("-------修改之前--------");
                    printColumn(rowData.getBeforeColumnsList());

                    System.out.println("-------修改之后--------");
                    printColumn(rowData.getAfterColumnsList());

                    updateRedis(rowData.getAfterColumnsList());
//                    updateDb(rowData.getAfterColumnsList(),tableName);
                }
            }
        }
    }


    private void updateRedis(List<Column> columns) {
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

    private void insertRedis(List<Column> columns) {
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

    private void deleteRedis(List<Column> columns) {
        if(CollectionUtils.isEmpty(columns)){
            return;
        }
        try(Jedis jedis = RedisUtil.getJedis()) {
            jedis.del(columns.get(0).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
