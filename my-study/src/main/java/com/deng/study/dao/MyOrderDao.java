package com.deng.study.dao;

import com.deng.study.dao.po.MyOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/12 19:58
 */
public interface MyOrderDao {
    void addMyOrder(MyOrder MyOrder);

    void addBatchMyOrderByThread(List<MyOrder> myOrderList);

    void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList);
}
