package com.deng.seata.dynamic.ds.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deng.seata.dynamic.ds.dao.mapper.ProductMapper;
import com.deng.seata.dynamic.ds.dao.po.Product;
import com.deng.seata.dynamic.ds.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private ProductMapper productMapper;

    @Override
    @DS("storage-ds")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void deductStock(Long productId, Long count) {
        String txNo = RootContext.getXID();
        log.info("deductStock 开始执行，事务ID------>{}",txNo);

        Product product = productMapper.selectByPrimaryKey(productId);
        if (Objects.isNull(product)) {
            log.info("商品信息为空,productId:{}", productId);
            throw new RuntimeException("商品信息为空");
        }

        int updateProductCount = productMapper.deductStockById(productId, count);
        if(updateProductCount <= 0){
            throw new RuntimeException("扣减冻结失败");
        }

        log.info("deductStock 执行结束，txNo:{}",txNo);
    }
}
