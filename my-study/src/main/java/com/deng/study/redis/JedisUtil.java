package com.deng.study.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
     * @param key
     * @param value   必须具有唯一性
     * @param timeOut
     * @return
     */
    public boolean tryLockWithNxEx(String key, String value,int timeOut, TimeUnit timeUnit) {
        int seconds = (int)timeUnit.toSeconds(timeOut);
        SetParams setParams = SetParams.setParams().nx().ex(seconds);
        return "OK".equals(getJedis().set(key, value, setParams));
    }

    /**
     * 使用lua脚本加锁
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public boolean tryLockWithLua(String key, String value, int seconds) {
        String luaScript = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        List<String> keys = new ArrayList<>();
        keys.add(key);

        List<String> values = new ArrayList<>();
        values.add(value);
        values.add(String.valueOf(seconds));
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
     * 使用lua脚本进行释放锁，保证原子性
     *
     * @param key
     * @param value
     * @return
     */
    public boolean releaseWithLua(String key, String value) {
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        List<String> keys = new ArrayList<>();
        keys.add(key);

        List<String> values = new ArrayList<>();
        values.add(value);

        return Objects.equals(getJedis().eval(luaScript, keys, values), 1L);
    }
}
