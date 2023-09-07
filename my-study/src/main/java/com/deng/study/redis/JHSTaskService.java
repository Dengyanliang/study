package com.deng.study.redis;

import com.deng.common.util.RandomUtil;
import com.deng.common.util.MyThreadUtil;
import com.deng.study.domain.ProductDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Desc: 模拟聚划算service
 * @Auther: dengyanliang
 * @Date: 2023/2/28 10:46
 */
@Slf4j
@Service
public class JHSTaskService  {
    private static final String JHS_KEY = "jhs";
    private static final String JHS_KEY_A = "jhs:a";
    private static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 模拟从数据库取20件商品，用于加载到聚划算的页面中
     * @return
     */
    private List<ProductDomain> getProductsFromMySql(){
        List<ProductDomain> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            int id = RandomUtil.getInt(10000);

            ProductDomain productDomain = new ProductDomain();
            productDomain.setId((long)id);
            productDomain.setTitle("product"+i);
            list.add(productDomain);
        }
        return list;
    }

    /**
     * 初始化有两种方式 一个是spring的init 一个是我们的@PostConstruct，
     * @PostConstruct 是依靠在applyBeanPostProcessorsBeforeInitialization时候执行InitDestroyAnnotationBeanPostProcessor
     * 后者是在这之后invokeInitMethods
     * 那么当我们实现BeanPostProcessor的时候postProcessBeforeInitialization 返回null，
     * applyBeanPostProcessorsBeforeInitialization在遇到null之后直接返回不会执行后续的BeanPostProcessor
     * 所以不会执行其他bean的@PostConstruct
     */
    @PostConstruct
    public void initJHS(){
        log.info("启动定时器模式天猫聚划算开始。。。。");
        new Thread(() -> {
            while (true) {
                // 从mysql查出数据，用于加载到redis并给聚划算页面展示
                final List<ProductDomain> list = this.getProductsFromMySql();

                atomic(list);

                // 暂停1分钟，间隔一分钟执行一次，模拟聚划算一天执行参加活动的品牌
                MyThreadUtil.sleep(1000 * 60);
            }
        }, "t1").start();
    }

    /**
     * 这里的两条语句不是原子性的，会出现问题
     * @param list
     */
    private void noAtomic(List<ProductDomain> list){
        // 采用redis list数据结构的lpush命令来实现存储
        redisTemplate.delete(JHS_KEY);
        // 加入最新的数据给redis参加活动
        redisTemplate.opsForList().leftPush(JHS_KEY,list);
    }

    /**
     * 为了解决缓存击穿
     * 1、新建
     * 这里开辟两块缓存，主A从B，先更新B再更新A，严格按照个顺序
     *
     * 2、查询
     * 先查询主缓存A，如果A没有（消失或失效了），再去查询B
     *
     * @param list
     */
    private void atomic(List<ProductDomain> list){
        // 先更新B缓存，且让B缓存过期时间超过A缓存，如果A突然失效了，还有B兜底，防止击穿
        redisTemplate.delete(JHS_KEY_B);
        redisTemplate.opsForList().leftPush(JHS_KEY_B,list);
        redisTemplate.expire(JHS_KEY_B,86420L, TimeUnit.SECONDS);

        // 再更新A
        redisTemplate.delete(JHS_KEY_A);
        redisTemplate.opsForList().leftPush(JHS_KEY_A,list);
        redisTemplate.expire(JHS_KEY_A,86400L,TimeUnit.SECONDS);

    }

}

