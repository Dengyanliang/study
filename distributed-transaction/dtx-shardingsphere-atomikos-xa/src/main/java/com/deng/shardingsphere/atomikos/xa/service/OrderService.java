package com.deng.shardingsphere.atomikos.xa.service;

import com.deng.shardingsphere.atomikos.xa.entity.PayOrder;
import com.deng.shardingsphere.atomikos.xa.entity.Product;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:27
 */
public interface OrderService {
    void updateOrder(PayOrder payOrder, Product product);
}
