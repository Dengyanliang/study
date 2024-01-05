package com.deng.study.service;


import com.deng.study.pojo.MyOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface MyOrderService {
    void addMyOrder(MyOrder MyOrder);

    void addBatchMyOrderByThread(List<MyOrder> myOrderList);

    void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList);
}
