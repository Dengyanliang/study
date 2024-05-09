package com.deng.seata.tcc.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.tcc.order.dao.mapper.OrdersMapper;
import com.deng.seata.tcc.order.dao.po.Orders;
import com.deng.seata.tcc.order.facade.request.OrderRequest;
import com.deng.seata.tcc.order.remote.client.AccountClient;
import com.deng.seata.tcc.order.remote.request.AccountRequest;
import com.deng.seata.tcc.order.remote.response.AccountResponse;
import com.deng.seata.tcc.order.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

// https://www.iocoder.cn/Spring-Cloud-Alibaba/Seata/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AccountClient accountClient;

    @Resource
    private OrdersMapper ordersMapper;



    @Override
    @GlobalTransactional // 开启全局事务
    public void addOrder(OrderRequest orderRequest) {

        log.info("事务ID------>{}", RootContext.getXID());

        // 新增订单
        Orders orders = new Orders();
        orders.setUserId(orderRequest.getUserId());
        orders.setProductId(orderRequest.getProductId());
        orders.setPayAmount(orderRequest.getAmount());
        orders.setPayStatus(PayStatusEnum.INIT.getCode());
        orders.setCount(orderRequest.getCount());
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());
        ordersMapper.insert(orders);

        AccountRequest request = new AccountRequest();
        request.setUserId(orderRequest.getUserId());
        request.setAmount(orderRequest.getAmount());
        AccountResponse response = accountClient.transfer(request);
        log.info("response:{}", JSON.toJSONString(response));

        int i =  10 / 0;
    }
}
