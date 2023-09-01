package com.deng.jta.bitronix.xa.service.impl;

import com.deng.jta.bitronix.xa.entity.PayOrder;
import com.deng.jta.bitronix.xa.entity.Product;
import com.deng.jta.bitronix.xa.mapper.db1.PayOrderMapper;
import com.deng.jta.bitronix.xa.mapper.db2.ProductMapper;
import com.deng.jta.bitronix.xa.service.OrderService;
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
    public void updateOrder(PayOrder payOrder, Product product) {

        payOrderMapper.updateById(payOrder);
        productMapper.updateById(product);
        int i = 10 / 0 ;
    }
}
