package com.deng.seata.saga.order.service;


import com.deng.seata.saga.order.facade.request.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);
}
