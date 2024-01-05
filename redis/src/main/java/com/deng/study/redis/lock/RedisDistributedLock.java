package com.deng.study.redis.lock;

import com.deng.common.util.MyThreadUtil;
import com.deng.study.redis.common.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: 自定义锁，实现Lock接口，要按照JUC中Lock接口的实现
 * 分布式锁必须具备的条件：
 * ----lua 脚本
 *   独占性：任意时刻只能有一个客户端拥有锁，不能被多个客户端获取
 *   原子性：
 *   防误删：锁只能被持有该锁的客户端删除，不能被其他客户端删除
 *   重入性：锁可以被同一个客户端端多次获取
 * --- 加过期时间
 *   防死锁：获取锁的客户端因某些原因宕机导致未能释放锁，那么其他客户端就无法获取该锁，需要有机制来避免该类问题的发生
 * --- 多节点部署，redssion
 *   高可用：当部分节点宕机时，客户端仍能获取锁或释放锁
 * --- lua 脚本+Timer定时任务，自旋
 *   自动续期：
 *
 * @Auther: dengyanliang
 * @Date: 2023/2/26 17:14
 */
@Component
public class RedisDistributedLock implements MyLock {

    @Autowired
    private JedisUtil jedisUtil;

    private String key;
    private String value;
    private long time;
    private long expireTime;
    private TimeUnit unit;

    public RedisDistributedLock() {
    }

    public RedisDistributedLock(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RedisDistributedLock(String key, String value, long time, long expireTime, TimeUnit unit) {
        this.key = key;
        this.value = value;
        this.time = time;
        this.expireTime = expireTime;
        this.unit = unit;
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public boolean tryLock() {
        return tryLock(key, value, time, expireTime, unit);
    }

    @Override
    public boolean tryLock(String key, String value, long time, long expireTime, TimeUnit unit) {
        boolean flag = jedisUtil.tryLockWithLua(key, value, time, unit);
        if (time == -1L) {
            // 自旋锁，这里是简化版的，一直自旋
            while (!flag) {
                MyThreadUtil.sleep(1000 * 10); // keypoint 一定要暂停一段时间再去自旋
                flag = jedisUtil.tryLockWithLua(key, value, time, unit);
            }
            // 自动续期
            jedisUtil.renewExpire(key, value, expireTime, unit);
            return true;
        }
        return flag;
    }

    @Override
    public void unlock() {
        jedisUtil.releaseLockWithLua(key, value);
    }
}
