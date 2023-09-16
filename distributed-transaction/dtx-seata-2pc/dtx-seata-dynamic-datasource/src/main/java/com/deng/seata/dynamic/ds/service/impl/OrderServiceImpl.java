package com.deng.seata.dynamic.ds.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.dynamic.ds.dao.mapper.OrdersMapper;
import com.deng.seata.dynamic.ds.dao.po.Orders;
import com.deng.seata.dynamic.ds.facade.request.OrderRequest;
import com.deng.seata.dynamic.ds.service.AccountService;
import com.deng.seata.dynamic.ds.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StorageServiceImpl storageService;

    @Resource
    private AccountService accountService;

    @Resource
    private OrdersMapper ordersMapper;


    @DS("order-ds")
    @GlobalTransactional
    @Override
    public void addOrder(OrderRequest orderRequest) {
        log.info("addOrder 开始执行，事务ID------>{}", RootContext.getXID());

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

        accountService.deductAmount(orderRequest.getUserId(),orderRequest.getAmount());

        storageService.deductStock(orderRequest.getProductId(),orderRequest.getCount());

        int i =  10 / 0;
    }
}
