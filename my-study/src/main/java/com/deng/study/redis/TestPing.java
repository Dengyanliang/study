package com.deng.study.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/2 20:00
 */
@Slf4j
public class TestPing {

    public static void main(String[] args) {
        // 测试连接
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String ping = jedis.ping();
        log.info("ping:{}",ping);

        log.info("清空数据:{}",jedis.flushDB());
        log.info("判断某个key是否存在:{}",jedis.exists("username"));

        log.info("新增username,zhangsan的键值对：{}", jedis.set("username","zhangsan"));
        log.info("新增password,password的键值对：{}", jedis.set("password","password"));
        log.info("系统中所有的键如下:");

        Set<String> keys = jedis.keys("*");
        log.info("keys:{}",keys);

        log.info("随机返回key空间的一个:{}",jedis.randomKey());

        log.info("删除键password:{}",jedis.del("password"));
        log.info("判断键password是否存在:{}",jedis.exists("password"));
        log.info("查看键username所存储的类型:{}",jedis.type("username"));

        log.info("重命名key:{}",jedis.rename("username","name"));

        log.info("取出更改后的key:{}",jedis.get("name"));
        log.info("按索引查询:{}",jedis.select(1));

        log.info("删除当前选择数据库的所有key:{}",jedis.flushDB());
        log.info("返回当前选择数据库中key的数目:{}",jedis.dbSize());
        log.info("清空所有数据库中的key:{}",jedis.flushAll());

    }
}
