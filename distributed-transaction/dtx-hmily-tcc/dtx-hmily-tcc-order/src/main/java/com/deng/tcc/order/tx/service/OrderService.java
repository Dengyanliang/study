package com.deng.tcc.order.tx.service;


import com.deng.tcc.order.tx.dao.po.Orders;
import com.deng.tcc.order.tx.facade.request.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);

    void tryAddOrder(Orders order);
}
