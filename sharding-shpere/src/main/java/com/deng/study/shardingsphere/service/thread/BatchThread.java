package com.deng.study.shardingsphere.service.thread;

import com.deng.study.shardingsphere.dao.mapper.OrderMapper;
import com.deng.study.shardingsphere.dao.po.Order;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/13 19:25
 */
public class BatchThread implements Runnable{

    private OrderMapper orderMapper;
    private List<Order> orderList;

    public BatchThread(OrderMapper orderMapper, List<Order> orderList) {
        this.orderMapper = orderMapper;
        this.orderList = orderList;
    }

    @Override
    public void run() {
        System.out.println("开始添加....");
        if(CollectionUtils.isNotEmpty(orderList)){
            orderMapper.insertBatchSomeColumn(orderList);
        }
        System.out.println("结束添加....添加总数为："  + orderList.size());
    }
}
