package com.deng.study.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.mapper.OrderMapper;
import com.deng.study.shardingsphere.dao.po.Order;
import com.deng.study.shardingsphere.service.OrderService;
import com.deng.study.shardingsphere.service.thread.BatchThread;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private int i = 0;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA) // 使用XA管理事务
    public void addOrder(Order order) {
        i++;
        orderMapper.insert(order);
        if(i == 5){
            throw new RuntimeException("测试报错");
        }
    }

    @Override
    public int batchAdd(List<Order> orderList) {

        BatchThread batchThread = new BatchThread(orderMapper,orderList);
        threadPoolExecutor.execute(batchThread);

        return 0;
    }

    @Override
    public Order getOrder(QueryWrapper<Order> queryWrapper ) {
        return orderMapper.selectOne(queryWrapper);
    }
}




