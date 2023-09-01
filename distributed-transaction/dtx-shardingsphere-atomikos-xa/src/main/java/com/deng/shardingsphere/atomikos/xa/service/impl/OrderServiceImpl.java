package com.deng.shardingsphere.atomikos.xa.service.impl;

import com.deng.shardingsphere.atomikos.xa.entity.PayOrder;
import com.deng.shardingsphere.atomikos.xa.entity.Product;
import com.deng.shardingsphere.atomikos.xa.mapper.PayOrderMapper;
import com.deng.shardingsphere.atomikos.xa.mapper.ProductMapper;
import com.deng.shardingsphere.atomikos.xa.service.OrderService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void updateOrder(PayOrder payOrder, Product product) {

        payOrderMapper.updateById(payOrder);
        productMapper.updateById(product);
        int i = 10 / 0 ;
    }
}
