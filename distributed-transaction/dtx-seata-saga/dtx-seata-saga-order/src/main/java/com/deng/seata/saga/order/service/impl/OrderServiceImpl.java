package com.deng.seata.saga.order.service.impl;

import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.saga.account.facade.BalanceAction;
import com.deng.seata.saga.order.dao.mapper.OrdersMapper;
import com.deng.seata.saga.order.dao.po.Orders;
import com.deng.seata.saga.order.facade.request.OrderRequest;
import com.deng.seata.saga.order.service.OrderService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrdersMapper ordersMapper;

    @Override
    public void addOrder(OrderRequest orderRequest) {
        log.info("addOrder 开始。。。。。。");

        // 新增订单
        Orders orders = new Orders();
        orders.setUserId(orderRequest.getUserId());
        orders.setProductId(orderRequest.getProductId());
        orders.setPayAmount(orderRequest.getAmount());
        orders.setPayStatus(PayStatusEnum.INIT.getCode());
        orders.setCount(orderRequest.getCount());
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());
        int addCount = ordersMapper.insert(orders);
        if(addCount <= 0){
            throw new RuntimeException("添加订单失败");
        }
    }
}
