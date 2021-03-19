package com.deng.study.dao;

import com.deng.study.dao.mapper.OrderMapper;
import com.deng.study.dao.po.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:05
 */
@Repository
public class OrderDaoImpl implements OrderDao{

    @Resource
    OrderMapper orderMapper;

    @Override
    public void insert(Order order) {
        orderMapper.insert(order);
    }
}
