package com.deng.study.redis;


/**
 * @Desc: 分布式锁的工厂
 * @Auther: dengyanliang
 * @Date: 2023/2/26 17:14
 */
public class DistributedLockFactory {

    private String key;
    private String value;
    private String uuid;

    public DistributedLockFactory(String key, String value, String uuid) {
        this.key = key;
        this.value = value;
        this.uuid = uuid;
    }

    public MyLock getDistributedLock(String lockType){
        if(lockType.equals("redis")){
            return new RedisDistributedLock(key,value);
        }else if(lockType.equals("zk")){

        }
        return null;
    }
}
