package com.deng.seata.tcc.order.service;


import com.deng.seata.tcc.order.facade.request.OrderRequest;

public interface OrderService {

    void addOrder(OrderRequest orderRequest);

}
