package com.deng.study.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁必须具备的条件：
 * ----lua 脚本
 *   独占性：任意时刻只能有一个客户端拥有锁，不能被多个客户端获取
 *   原子性：
 *   防误删：锁只能被持有该锁的客户端删除，不能被其他客户端删除
 *   重入性：锁可以被同一个客户端端多次获取
 * --- 加过期时间
 *   防死锁：获取锁的客户端因某些原因宕机导致未能释放锁，那么其他客户端就无法获取该锁，需要有机制来避免该类问题的发生
 * --- lua 脚本+Timer定时任务，自旋
 *   自动续期：
 * --- 多节点部署，redssion
 *   高可用：当部分节点宕机时，客户端仍能获取锁或释放锁
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 创建连接池对象
     * @return
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    public void close(Jedis jedis){
        if(Objects.nonNull(jedis)){
            jedis.close();
        }
    }

    public void close(JedisPool jedisPool){
        if(Objects.nonNull(jedisPool)){
            jedisPool.close();
        }
    }

    /**
     * 错误的做法，不能保证setnx和expire的原子性
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public boolean tryLock(String key, String value, int seconds) {
        Long result = getJedis().setnx(key, value);
        // 等于1表示设置成功
        if (Objects.nonNull(result) && result == 1L) {
            return getJedis().expire(key, seconds) == 1;
        }
        return false;
    }

    /**
     * 使用setNx加锁，只能解决有无的问题，够用但是不够完美
     *
     * @param key
     * @param value   必须具有唯一性
     * @param timeOut
     * @return
     */
    public boolean tryLockWithNxEx(String key, String value,int timeOut, TimeUnit timeUnit) {
        int seconds = (int)timeUnit.toSeconds(timeOut);
        SetParams setParams = SetParams.setParams().nx().ex(seconds); // 设置过期时间
        return "OK".equals(getJedis().set(key, value, setParams));
    }

    /**
     * 获取锁
     * 使用lua脚本加锁，保证原子性、可重入性、防死锁、独占性
     *
     * @param key
     * @param value
     * @param time 时间
     * @param unit 单位
     * @return
     */
    public boolean tryLockWithLua(String key, String value, long time, TimeUnit unit) {
        // 该脚本实现实现是否存在，但是实现不了可重入
//        String luaScript = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";

        // 这个脚本可以实现是否存在，以及可重入
        String luaScript = "if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then redis.call('hincrby',KEYS[1],ARGV[1],1) redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        List<String> keys = new ArrayList<>();
        keys.add(key);

        List<String> values = new ArrayList<>();
        values.add(value);
        values.add(String.valueOf(unit.toSeconds(time)));
        return Objects.equals(getJedis().eval(luaScript, keys, values), 1L);
    }

    /**
     * 错误的做法，释放锁
     *
     * @param key
     */
    public void wrongReleaseLock(String key){
        getJedis().del(key);
    }

    /**
     * 释放锁
     * 使用lua脚本进行释放锁，保证原子性和可重入性
     *
     * @param key
     * @param value
     * @return
     */
    public boolean releaseLockWithLua(String key, String value) {
        // 能保证原子性
//        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        // 能保证原子性和可重入性 TODO 在key不存在的时候，会返回null，java代码还没有测试
        String luaScript = "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 0 then return nil elseif redis.call('HINCRBY',KEYS[1],ARGV[1],-1) == 0 then return redis.call('del',KEYS[1]) else return 0 end";

        List<String> keys = new ArrayList<>();
        keys.add(key);

        List<String> values = new ArrayList<>();
        values.add(value);

        return Objects.equals(getJedis().eval(luaScript, keys, values), 1L);
    }

    /**
     * 自动续期
     * @param key
     * @param value
     * @param expireTime 续期时间
     * @param unit
     */
    public void renewExpire(String key, String value,long expireTime, TimeUnit unit){
        String luaScript = "if redis.call('hexists',KEYS[1],ARGV[1]) == 1 then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end";

        long expireTimeSeconds = unit.toSeconds(expireTime); // 把传入的时间转化为秒

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                List<String> keys = new ArrayList<>();
                keys.add(key);

                List<String> values = new ArrayList<>();
                values.add(value);
                values.add(String.valueOf(expireTimeSeconds));

                Object eval = getJedis().eval(luaScript, keys, values);
                // 如果存在，表示业务逻辑还没有执行完，所以需要续期
                boolean flag = Objects.equals(eval, 1L);
                if(flag){
                    renewExpire(key, value, expireTime, unit);
                }
            }
        },(expireTimeSeconds * 1000) / 3);
    }
}
