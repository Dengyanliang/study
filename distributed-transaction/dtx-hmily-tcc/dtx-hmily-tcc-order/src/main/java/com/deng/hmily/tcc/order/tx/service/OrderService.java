package com.deng.hmily.tcc.order.tx.service;


import com.deng.hmily.tcc.order.tx.dao.po.Orders;
import com.deng.hmily.tcc.order.tx.facade.request.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);

    void tryAddOrder(Orders order);
}
