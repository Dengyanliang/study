package com.deng.study.shardingsphere.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.po.Order;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface OrderService {
    void addOrder(Order order);

    int batchAdd(List<Order> orderList);

    Order getOrder(QueryWrapper<Order> queryWrapper );
}
