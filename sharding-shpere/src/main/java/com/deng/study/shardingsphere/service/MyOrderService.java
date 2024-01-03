package com.deng.study.shardingsphere.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.study.shardingsphere.dao.po.MyOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface MyOrderService {
    void addMyOrder(MyOrder MyOrder);

    void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList);

    MyOrder getMyOrder(QueryWrapper<MyOrder> queryWrapper);

    IPage<MyOrder> getMyOrderByPage(IPage<MyOrder> page, QueryWrapper<MyOrder> queryWrapper);
}
