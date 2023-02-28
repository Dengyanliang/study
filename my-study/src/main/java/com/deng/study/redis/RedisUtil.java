package com.deng.study.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * @Desc: redis工具类，方便main方法测试
 * @Auther: dengyanliang
 * @Date: 2023/2/27 21:15
 */
public class RedisUtil {

    private static JedisPool jedisPool;
    public static final String REDIS_HOST_IP = "127.0.0.1";
    public static final Integer REDIS_PORT = 6379;
    public static final Integer TIMEOUT = 10000;
    public static final String REDIS_PASSWORD = "123456";

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(20);
        config.setMinIdle(20);
        jedisPool = new JedisPool(config, REDIS_HOST_IP, REDIS_PORT, TIMEOUT, REDIS_PASSWORD);
    }

    public static Jedis getJedis(){
        if(Objects.nonNull(jedisPool)){
            return jedisPool.getResource();
        }
        throw new RuntimeException("JedisPool is not ok");
    }
}
