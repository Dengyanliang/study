package com.deng.seata.order.tx.service;


public interface OrderService {
    boolean addOrder(Integer id,Long amount);
}
