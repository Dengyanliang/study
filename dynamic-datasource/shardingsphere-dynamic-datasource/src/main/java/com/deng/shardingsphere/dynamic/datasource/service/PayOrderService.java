package com.deng.shardingsphere.dynamic.datasource.service;


import com.deng.shardingsphere.dynamic.datasource.entity.PayOrder;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface PayOrderService {
    void addOrder(PayOrder payOrder);

    PayOrder getOrder(Long id, Long productId);
}
