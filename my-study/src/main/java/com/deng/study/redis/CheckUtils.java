package com.deng.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc: 校验是否存在
 * @Auther: dengyanliang
 * @Date: 2023/2/28 18:30
 */
@Slf4j
@Component
public class CheckUtils {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean checkWithBloomFilter(String checkItem,String key){
        int hashValue = Math.abs(key.hashCode());
        long index = (long)(hashValue % Math.pow(2,32));
        boolean existOk = redisTemplate.opsForValue().getBit(checkItem,index);
        log.info("---key：{}对应的坑位下标index：{} 是否存在：{}",key,index,existOk);

        return existOk;
    }
}
