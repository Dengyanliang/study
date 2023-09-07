package com.deng.study.redis;

import com.deng.common.util.MyThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Desc: 自定义锁，实现Lock接口，要按照JUC中Lock接口的实现
 * @Auther: dengyanliang
 * @Date: 2023/2/26 17:14
 */
public class RedisDistributedLock implements MyLock {

    @Autowired
    private JedisUtil jedisUtil;

    private String key;
    private String value;
    private long time;
    private long expireTime;
    private TimeUnit unit;

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
