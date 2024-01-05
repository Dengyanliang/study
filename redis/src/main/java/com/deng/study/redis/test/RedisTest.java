package com.deng.study.redis.test;

import com.alibaba.fastjson.JSONObject;
import com.deng.study.redis.common.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisTest {

    private static int NUM = 500;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JedisUtil jedisUtil;

    @Test
    public void testBasicByJedis() {
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

    @Test
    public void testTxByJedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);

        jedis.flushAll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("age",18);
        String result = jsonObject.toJSONString();
        Transaction multi = jedis.multi();
        try {
            multi.set("user1",result);
            multi.set("user2",result);

            int i = 1 /0;
            log.info("i:{}",i);       // 测试异常的情况

            multi.exec();

        }catch (Exception e){
            multi.discard();
            log.info("orrur Exception e",e);
        }finally {
            log.info("user1:{}",jedis.get("user1"));
            log.info("user2:{}",jedis.get("user2"));
            jedis.close();
        }
    }

    @Test
    public void testRedisTemplate(){
        // redisTemplate
        // opsForValue 操作字符串 类似于String
        // opsForList  操作List  类似于list
        // opsForHash
        // opsForGeo
        // opsForZSet
        // opsForSet
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.flushAll();

        redisTemplate.opsForValue().set("key","value1");
        redisTemplate.exec();

        String value = (String) redisTemplate.opsForValue().get("key");
        log.info("value:{}",value);
    }


    /**
     * 测试秒杀
     */
    private static void secskill(){
        log.info("-----------------");
        if(NUM <= 0){
            log.info("已经抢光了....");
            return;
        }
        log.info("NUM:{}",NUM--);
    }


    @Test
    public void testJedis(){

        String key = "key";
        String value = UUID.randomUUID().toString();
        int seconds = 5;

        jedisUtil.tryLockWithLua(key, value, seconds, TimeUnit.SECONDS);

        secskill();
        jedisUtil.releaseLockWithLua(key,value);

//        Runnable runnable = () -> {
//            String key = "key";
//            String value = UUID.randomUUID().toString();
//            int seconds = 5;
//
//            jedisUtil.tryLockWithLua(key,value,seconds);
//
//            secskill();
//            jedisUtil.releaseLockWithLua(key,value);
////                redisLock.wrongReleaseLock(key); // 不能保证原子性
//        };

//        for (int i = 0; i < 1000; i++) {
//            Thread t = new Thread(runnable);
//            t.start();
//        }
    }

    @Test
    public void testHash(){
        System.out.println("Aa".hashCode());
        System.out.println("BB".hashCode());

        Set<Integer> set = new HashSet<>();
        int hashCode;
        for (int i = 0; i < 200000; i++) {
            hashCode = new Object().hashCode();
            if(set.contains(hashCode)){
                System.out.println("运行到"+i+"出现了hash冲突，hashcode：" + hashCode);
                continue;
            }
            set.add(hashCode);
        }
    }

    public static void main(String[] args) {
//        final JedisUtil finalJedisUtil = jedisUtil;
//
//        Runnable runnable = () -> {
//            String key = "key";
//            String value = UUID.randomUUID().toString();
//            int seconds = 5;
//
//            finalJedisUtil.tryLockWithLua(key,value,seconds);
//
//            secskill();
//            finalJedisUtil.releaseWithLua(key,value);
////                redisLock.wrongReleaseLock(key); // 不能保证原子性
//        };
//
//        for (int i = 0; i < 1000; i++) {
//            Thread t = new Thread(runnable);
//            t.start();
//        }
    }


}

