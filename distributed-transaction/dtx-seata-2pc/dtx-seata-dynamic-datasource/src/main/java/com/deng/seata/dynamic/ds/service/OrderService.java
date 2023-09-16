package com.deng.seata.dynamic.ds.service;


import com.deng.seata.dynamic.ds.facade.request.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);
}
