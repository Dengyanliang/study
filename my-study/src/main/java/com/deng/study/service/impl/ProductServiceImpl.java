package com.deng.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.study.common.constant.RedisConstant;
import com.deng.study.dao.mapper.ProductMapper;
import com.deng.study.dao.po.Product;
import com.deng.study.redis.CheckUtils;
import com.deng.study.service.ProductService;
import com.deng.study.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
    private CheckUtils checkUtils;

    @Override
    public void add() {
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setName("product.name" + i);
            product.setPrice((long) RandomUtil.getInt(100));
            productMapper.insert(product);
        }
    }

    @Override
    public Product findProductByIdWithBloomFilter(Integer productId) {
        Product product = null;
        String key = RedisConstant.CACHE_KEY_PRODUCT + productId;

        // 布隆过滤器check，如果布隆过滤器没有，则绝对没有；如果有，则有可能有
        if (!checkUtils.checkWithBloomFilter(RedisConstant.WHITE_LIST_PRODUCT, key)) {
            log.info("白名单无此商品，不可访问：{}",key);
            return product;
        }

        // 查询redis
        product = (Product)redisTemplate.opsForValue().get(key);
        // redis没有，进一步查询数据库
        if(Objects.isNull(product)){
            // 查询数据库
            product = productMapper.selectByPrimaryKey(productId.longValue());
            if(Objects.nonNull(product)){
                // 把数据放入到redis
                redisTemplate.opsForValue().set(key,product);
            }
        }
        log.info("最后返回的数据：{}", JSON.toJSONString(product));
        return product;
    }
}
