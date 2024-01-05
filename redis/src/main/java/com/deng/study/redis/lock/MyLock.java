package com.deng.study.redis.lock;


import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/26 20:00
 */
public interface MyLock {
    void lock();

    boolean tryLock();

    boolean tryLock(String key, String value, long time, long expireTime, TimeUnit unit);

    void unlock();
}
