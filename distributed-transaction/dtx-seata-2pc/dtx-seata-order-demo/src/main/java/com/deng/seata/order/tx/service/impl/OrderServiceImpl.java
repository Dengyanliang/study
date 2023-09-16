package com.deng.seata.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.order.tx.dao.mapper.OrdersMapper;
import com.deng.seata.order.tx.dao.po.Orders;
import com.deng.seata.order.tx.facade.request.OrderRequest;
import com.deng.seata.order.tx.remote.client.AccountClient;
import com.deng.seata.order.tx.remote.request.AccountRequest;
import com.deng.seata.order.tx.remote.response.AccountResponse;
import com.deng.seata.order.tx.service.OrderService;
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


    // @Transactional 这个注解在AT模式下必须加上，在XA模式下可以不要，所以都加上吧
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    @Override
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
        int addCount = ordersMapper.insert(orders);
        if(addCount <= 0){
            throw new RuntimeException("添加订单失败");
        }

        AccountRequest request = new AccountRequest();
        request.setUserId(orderRequest.getUserId());
        request.setAmount(orderRequest.getAmount());
        AccountResponse response = accountClient.transfer(request);
        log.info("response:{}", JSON.toJSONString(response));

        int i =  10 / 0;
    }
}
