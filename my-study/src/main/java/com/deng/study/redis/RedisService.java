package com.deng.study.redis;

import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/26 10:03
 */
@Slf4j
@Service
public class RedisService {

    private static final String REDIS_KEY_PREFIX = "redisLock_";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JedisUtil jedisUtil;

    @Value("${server.port}")
    private String port;

    public String sale(String queryKey){
        return "";
    }

    public String saleVersion06(String queryKey){
        String retMessage = "";
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return retMessage + "，服务端口号" + port;
    }
    /**
     * 使用lua脚本释放锁，防止锁被其他线程释放。也就是避免违反乱释放特性
     * @param queryKey
     * @return
     */
    public String saleVersion05(String queryKey){
        String retMessage = "";
        String redisLockKey = REDIS_KEY_PREFIX + queryKey;
        String redisLockValue = UUID.randomUUID().toString()+":"+Thread.currentThread().getId();
        while (!redisTemplate.opsForValue().setIfAbsent(redisLockKey, redisLockValue, 30, TimeUnit.SECONDS)) {
            ThreadUtil.sleep(20);
        }
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } finally {
            jedisUtil.releaseLockWithLua(redisLockKey,redisLockValue);
        }
        return retMessage + "，服务端口号" + port;
    }

    /**
     * 防止虚假唤醒
     * @param queryKey
     * @return
     */
    public String saleVersion04(String queryKey){
        String retMessage = "";
        String redisLockKey = REDIS_KEY_PREFIX + queryKey;
        String redisLockValue = UUID.randomUUID().toString()+":"+Thread.currentThread().getId();
        if (!redisTemplate.opsForValue().setIfAbsent(redisLockKey, redisLockValue, 30, TimeUnit.SECONDS)) {
            ThreadUtil.sleep(20);
        }
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } finally {
            jedisUtil.releaseLockWithLua(redisLockKey,redisLockValue);
        }
        return retMessage + "，服务端口号" + port;
    }



    /**
     * 加上过期时间
     * @param queryKey
     * @return
     */
    public String saleVersion03(String queryKey){
        String retMessage = "";
        String redisLockKey = REDIS_KEY_PREFIX + queryKey;
        String redisLockValue = UUID.randomUUID().toString()+":"+Thread.currentThread().getId();
        while (!redisTemplate.opsForValue().setIfAbsent(redisLockKey, redisLockValue, 30, TimeUnit.SECONDS)) {
            ThreadUtil.sleep(20);
        }
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } finally {
            jedisUtil.releaseLockWithLua(redisLockKey,redisLockValue);
        }
        return retMessage + "，服务端口号" + port;
    }


    /**
     * 使用redis，但是没有过期时间
     * @param queryKey
     * @return
     */
    public String saleVersion02(String queryKey){
        String retMessage = "";
        String redisLockKey = REDIS_KEY_PREFIX + queryKey;
        String redisLockValue = UUID.randomUUID().toString()+":"+Thread.currentThread().getId();
//        while () {
//            ThreadUtil.sleep(20);
//        }
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } finally {
            jedisUtil.releaseLockWithLua(redisLockKey,redisLockValue);
        }
        return retMessage + "，服务端口号" + port;
    }

    /**
     * 使用lock或者synchronized加锁，只适合一个JVM的方式
     * @param queryKey
     * @return
     */
    public String saleVersion01(String queryKey){
        String retMessage = "";
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            String result = redisTemplate.opsForValue().get(queryKey);
            Integer inventoryNumber = StringUtils.isBlank(result)?0: Integer.parseInt(result);
            if(inventoryNumber > 0){
                redisTemplate.opsForValue().set("",String.valueOf(--inventoryNumber));
                retMessage="成功卖出一个商品，库存剩余：" + inventoryNumber;
                System.out.println(retMessage + " ，服务端口号：" + port);
            }else{
                retMessage = "商品卖完了";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return retMessage + "，服务端口号" + port;
    }

}
