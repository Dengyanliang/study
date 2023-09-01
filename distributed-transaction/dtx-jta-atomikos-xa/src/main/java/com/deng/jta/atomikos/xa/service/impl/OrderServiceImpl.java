package com.deng.jta.atomikos.xa.service.impl;

import com.deng.jta.atomikos.xa.entity.PayOrder;
import com.deng.jta.atomikos.xa.entity.Product;
import com.deng.jta.atomikos.xa.mapper.db1.PayOrderMapper;
import com.deng.jta.atomikos.xa.mapper.db2.ProductMapper;
import com.deng.jta.atomikos.xa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public void updateOrder(PayOrder payOrder, Product product) {

        payOrderMapper.updateById(payOrder);
        productMapper.updateById(product);
//        int i = 10 / 0 ;
    }
}
