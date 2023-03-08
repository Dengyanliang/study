package com.deng.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.study.common.constant.RedisConstant;
import com.deng.study.dao.mapper.ProductMapper;
import com.deng.study.dao.po.Product;
import com.deng.study.domain.SeckillVoucher;
import com.deng.study.redis.BloomFilter;
import com.deng.study.service.ProductService;
import com.deng.study.util.RandomUtil;
import com.deng.study.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 18:36
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private BloomFilter bloomFilter;

    private BlockingQueue<Product> orderTasks = new ArrayBlockingQueue<>(1024);
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    @PostConstruct
    private void init(){
        pool.submit(new ProductOrderHandler());
    }

    private class ProductOrderHandler implements Runnable{
        @Override
        public void run() {
            while (true) {
                try {
                    // 获取队列中的订单信息
                    Product product = orderTasks.take();

                    // 创建订单

                } catch (Exception e) {
                    log.error("product出现异常", e);
                }
            }
        }
    }



    @Override
    public void add() {
        String key = "";
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setName("product.name" + i);
            product.setPrice((long) RandomUtil.getInt(100));
            productMapper.insert(product);

            key = getProductRedisKey(product.getId());
            redisTemplate.opsForValue().set(key,product);
        }
    }

    @Override
    public Product findProductById(Long productId) {
        return findProductByDoubleCheck(productId);
    }

    public void addSeckillVoucher(SeckillVoucher voucher){
        SeckillVoucher seckillVoucher = new SeckillVoucher();
        BeanUtils.copyProperties(voucher,seckillVoucher);
        // 保存到数据库

        // 保存到缓存
        redisTemplate.opsForValue().set(RedisConstant.SECKILL_STOCK_KEY + voucher.getId(), seckillVoucher);
    }


    /**
     * TODO 缓存击穿的时候
     *      不明白为啥用逻辑过期
     * @param productId
     * @return
     */
    private Product queryWithLogicalExpire(Long productId){

        return null;
    }

    /**
     * 查询数据
     * 1、使用双检加锁策略--防止缓存击穿
     * 2、缓存空对象--防止缓存穿透
     * @param productId
     * @return
     */
    private Product findProductByDoubleCheck(Long productId) {
        Product product = null;
        String key = getProductRedisKey(productId);

        // 第一遍查询redis
        product = (Product)redisTemplate.opsForValue().get(key);

        // redis没有，进一步查询数据库
        if (Objects.isNull(product)) {
            String lockProductRedisKey = getLockProductRedisKey(productId);
            // 获取锁
            final boolean flag = tryLock(lockProductRedisKey);
            if (!flag) {
                ThreadUtil.sleep(100);          // 等待
                findProductByDoubleCheck(productId);  // 重新获取锁
            }
            try {
                // 第二遍查询redis
                product = (Product) redisTemplate.opsForValue().get(key);
                if (Objects.isNull(product)) { // keypoint 这里就是双检加锁机制
                    // 查询数据库
                    product = productMapper.selectByPrimaryKey(productId);
                    if (Objects.nonNull(product)) {
                        // 把数据放入到redis
                        redisTemplate.opsForValue().setIfAbsent(key, product, 1, TimeUnit.DAYS);
                    } else {
                        product = new Product();
                        // keypoint 防止缓存穿透，存入空对象，存放的时间短一些
                        redisTemplate.opsForValue().setIfAbsent(key, product, 5, TimeUnit.MINUTES);
                    }
                }
            } finally {
                unLock(lockProductRedisKey);
            }
        }
        log.info("最后返回的数据：{}", JSON.toJSONString(product));
        return product;
    }

    private boolean tryLock(String key){
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtils.isTrue(flag);
    }

    private void unLock(String key){
        redisTemplate.delete(key);
    }

    /**
     * 使用布隆过滤器查询数据，防止缓存穿透
     * @param productId
     * @return
     */
    @Override
    public Product findProductByIdWithBloomFilter(Long productId) {
        Product product = null;
        String key = getProductRedisKey(productId);

        // 布隆过滤器check，如果布隆过滤器没有，则绝对没有；如果有，则有可能有
        if (!bloomFilter.checkWithBloomFilter(RedisConstant.WHITE_LIST_PRODUCT, key)) {
            log.info("白名单无此商品，不可访问：{}",key);
            return product;
        }

        // 查询redis
        product = (Product)redisTemplate.opsForValue().get(key);
        // redis没有，进一步查询数据库
        if(Objects.isNull(product)){
            // 查询数据库
            product = productMapper.selectByPrimaryKey(productId.longValue());
            if (Objects.nonNull(product)) {
                // 把数据放入到redis
                redisTemplate.opsForValue().setIfAbsent(key, product, 1, TimeUnit.DAYS);
            }
        }
        log.info("最后返回的数据：{}", JSON.toJSONString(product));
        return product;
    }

    /**
     * 缓存更新策略：先更新数据库，再删除缓存
     * @param product
     * @return
     */
    @Override
    public boolean updateProduct(Product product) {
        final Long id = product.getId();
        if(Objects.isNull(id)){
            return false;
        }
        // 先更新数据库
        productMapper.updateByPrimaryKeySelective(product);

        // 再删除缓存
        redisTemplate.delete(getProductRedisKey(id));

        return true;
    }

    private String getProductRedisKey(Long productId){
        return RedisConstant.CACHE_KEY_PRODUCT + productId;
    }

    private String getLockProductRedisKey(Long productId){
        return RedisConstant.LOCK_KEY_PRODUCT + productId;
    }
}
