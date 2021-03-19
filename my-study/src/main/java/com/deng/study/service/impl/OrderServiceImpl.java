package com.deng.study.service.impl;

import com.deng.study.dao.OrderDao;
import com.deng.study.dao.mapper.OrderMapper;
import com.deng.study.dao.po.Order;
import com.deng.study.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Resource
    OrderMapper orderMapper;

    @Override
    public void addOrder(Order order) {
        orderMapper.insert(order);
    }
}



