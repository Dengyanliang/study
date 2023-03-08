package com.deng.study.redis;

import com.deng.study.common.constant.RedisConstant;
import com.deng.study.dao.mapper.ProductMapper;
import com.deng.study.dao.po.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc: 布隆过滤器
 * @Auther: dengyanliang
 * @Date: 2023/2/28 15:37
 */
@Slf4j
@Component
public class BloomFilter {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ProductMapper productMapper;

    /**
     * 往布隆过滤器中初始化一些数据
     */
    @PostConstruct
    public void init(){
        List<Product> products = productMapper.selectByExample(null);
        if(CollectionUtils.isNotEmpty(products)){
            for (Product product : products) {
                log.info("-----BloomFilter..init.....");
                // 白名单数据加载到布隆过滤器
                String key = RedisConstant.CACHE_KEY_PRODUCT + product.getId();

                // 计算hashValue，由于存在计算出来可能负数的可能，这里取绝对值
                int hashValue = Math.abs(key.hashCode());

                // 通过hashValue和2的32次方后取余，获取对应的下标坑位
                long index = (long)(hashValue % Math.pow(2,32));
                log.info("{} 对应的坑位index:{}", key, index);

                // 设置redis里面的bitmap对应类型的白名单：whitelistProduct的坑位，
                redisTemplate.opsForValue().setBit(RedisConstant.WHITE_LIST_PRODUCT,index,true);
            }
        }
    }


    public boolean checkWithBloomFilter(String checkItem,String key){
        int hashValue = Math.abs(key.hashCode());
        long index = (long)(hashValue % Math.pow(2,32));
        boolean existOk = redisTemplate.opsForValue().getBit(checkItem,index);
        log.info("---key：{}对应的坑位下标index：{} 是否存在：{}",key,index,existOk);

        return existOk;
    }
}
