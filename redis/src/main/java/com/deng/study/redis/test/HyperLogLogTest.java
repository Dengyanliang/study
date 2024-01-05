package com.deng.study.redis.test;

import com.deng.common.util.MyThreadUtil;
import com.deng.common.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/3/3 20:49
 */
@Slf4j
@Service
public class HyperLogLogTest {

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void initIp(){
        new Thread(() -> {
            String ip = "";
            for (int i = 0; i < 100; i++) {
                ip = RandomUtil.getInt(256)+"."+
                        RandomUtil.getInt(256)+"." +
                        RandomUtil.getInt(256)+"."+
                        RandomUtil.getInt(256);
                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip={}，该IP地址访问首页的次数={}", ip, hll);
                MyThreadUtil.sleep(30);
            }

            Long count = redisTemplate.opsForHyperLogLog().size("hll");
            log.info("HyperLogLogService count：{}",count);

        }, "t1").start();
    }
}
