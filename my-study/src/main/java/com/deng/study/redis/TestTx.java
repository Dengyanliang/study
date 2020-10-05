package com.deng.study.redis;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/2 20:19
 */
@Slf4j
public class TestTx {
    public static void main(String[] args) {
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
}
