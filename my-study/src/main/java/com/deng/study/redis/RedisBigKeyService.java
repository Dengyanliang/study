package com.deng.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;

/**
 * @Desc: 大key操作
 * @Auther: dengyanliang
 * @Date: 2023/3/1 11:09
 */
@Slf4j
@Service
public class RedisBigKeyService {
    private static int count = 0;


    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        String bigKey = "whitelistProduct";

        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            bigKey = "k" + i;
            new RedisBigKeyService().delBigString(jedis,bigKey);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---costTime：" + (endTime-startTime) + "毫秒");
    }
    @Test
    public void test(){
        String str ="";
        for (int i = 1; i < 20; i++) {
            str+=i;
        }
        System.out.println(str);
        int c = 2^30; // 1024byte * 1024 * 1024 = 1G
        int c2 = c * 2^4; // 1G * 4 = 4G
        System.out.println(c);
    }

    /**
     * 如果是字符串，直接用del删除
     * @param bigStrKey
     */
    public void delBigString(Jedis jedis,String bigStrKey){
        Long del = jedis.del(bigStrKey);
        log.debug("del：{},delCount：{}", del, count++);
    }

    public void delBigHash(String bigHashKey){
        Jedis jedis = RedisUtil.getJedis();
        ScanParams scanParams = new ScanParams().count(100);
        String cursor = "0";
    }

}
