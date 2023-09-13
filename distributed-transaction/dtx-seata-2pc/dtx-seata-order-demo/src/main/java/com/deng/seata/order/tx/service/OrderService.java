package com.deng.seata.order.tx.service;


import com.deng.seata.order.tx.facade.request.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);
}
