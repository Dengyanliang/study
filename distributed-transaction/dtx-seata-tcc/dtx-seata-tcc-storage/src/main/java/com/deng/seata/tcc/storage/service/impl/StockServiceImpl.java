package com.deng.seata.tcc.storage.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.seata.tcc.storage.dao.mapper.ProductMapper;
import com.deng.seata.tcc.storage.dao.po.Product;
import com.deng.seata.tcc.storage.facade.request.StockRequest;
import com.deng.seata.tcc.storage.service.StockService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tryFreeze(StockRequest request) {

        Product product = getProduct(request);
        product.setFreezeStock(product.getFreezeStock() + request.getCount());

        // try 冻结库存=冻结库存+count
        int count = productMapper.updateById(product);
        if(count <= 0){
            throw new RuntimeException("增加冻结库存失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitFreeze(StockRequest stockRequest) {

        Product product = getProduct(stockRequest);
        product.setFreezeStock(product.getFreezeStock() - stockRequest.getCount());
        product.setStock(product.getStock() - stockRequest.getCount());

        // confirm 冻结库存=冻结库存-count，库存=库存-count
        int count = productMapper.updateById(product);
        if(count <= 0){
            throw new RuntimeException("扣减库存和冻结库存失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelFreeze(StockRequest stockRequest){

        Product product = getProduct(stockRequest);
        product.setFreezeStock(product.getFreezeStock() - stockRequest.getCount());

        // cancel  冻结库存=冻结库存-count
        int count = productMapper.updateById(product);
        if(count <= 0){
            throw new RuntimeException("扣减冻结库存失败");
        }
    }

    private Product getProduct(StockRequest request){
        Product product = productMapper.selectById(request.getProductId());
        if (Objects.isNull(product)) {
            log.info("商品信息为空，request:{}", JSON.toJSONString(request));
            throw new RuntimeException("商品信息为空");
        }
        return product;
    }
}
