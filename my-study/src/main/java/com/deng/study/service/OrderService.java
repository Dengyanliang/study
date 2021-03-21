package com.deng.study.service;

import com.deng.study.dao.po.PayOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface OrderService {
    void addOrder(PayOrder payOrder);
    void addBatchOrder(List<PayOrder> orderList);

    void addBatchOrder2();

    PayOrder getOrder(Long id);

    PayOrder getOrder2(Long id);
}
