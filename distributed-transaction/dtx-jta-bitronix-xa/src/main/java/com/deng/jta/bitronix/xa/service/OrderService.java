package com.deng.jta.bitronix.xa.service;


import com.deng.jta.bitronix.xa.entity.PayOrder;
import com.deng.jta.bitronix.xa.entity.Product;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:27
 */
public interface OrderService {
    void updateOrder(PayOrder payOrder, Product product);
}
