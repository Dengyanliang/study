package com.deng.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

import static com.deng.common.constant.RedisConstant.*;

/**
 * @Desc: redis工具类，方便main方法测试
 * @Auther: dengyanliang
 * @Date: 2023/2/27 21:15
 */
public class RedisUtil {

    private static JedisPool jedisPool;


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
