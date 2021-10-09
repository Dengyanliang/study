package com.deng.tcc.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.tcc.order.tx.dao.mapper.OrdersMapper;
import com.deng.tcc.order.tx.dao.po.Orders;
import com.deng.tcc.order.tx.remote.client.AccountClient;
import com.deng.tcc.order.tx.remote.request.AccountRequest;
import com.deng.tcc.order.tx.remote.response.AccountResponse;
import com.deng.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class OrderBankServiceImpl implements OrderService {

    @Autowired
    private AccountClient accountClient;

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    @Override
    public boolean addOrder(Integer id, Long amount) {

        // 新增订单
        Orders orders = new Orders();
        orders.setUserId(id);
        orders.setProductId(2);
        orders.setPayAmount(new BigDecimal(amount));
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());

        ordersMapper.insert(orders);

        AccountRequest request = new AccountRequest();
        request.setUserId(id);
        request.setAmount(amount);
        AccountResponse response = accountClient.transfer(request);
        log.info("response:{}", JSON.toJSONString(response));
        return true;
    }
}
