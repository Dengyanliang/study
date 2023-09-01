package com.deng.jta.atomikos.xa.service;

import com.deng.jta.atomikos.xa.entity.PayOrder;
import com.deng.jta.atomikos.xa.entity.Product;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:27
 */
public interface OrderService {
    void updateOrder(PayOrder payOrder, Product product);
}
