package com.deng.bank1.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.bank1.tx.entity.Orders;
import com.deng.bank1.tx.mapper.OrdersMapper;
import com.deng.bank1.tx.remote.client.BankClient;
import com.deng.bank1.tx.remote.request.AccountRequest;
import com.deng.bank1.tx.remote.response.AccountResponse;
import com.deng.bank1.tx.service.BankService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

// https://www.iocoder.cn/Spring-Cloud-Alibaba/Seata/
@Slf4j
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankClient bankClient;

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    @GlobalTransactional
    @Override
    public boolean transfer(Integer id, Long amount) {

        // 新增订单
        Orders orders = new Orders();
        orders.setId(1);
        orders.setUserId(id);
        orders.setProductId(1);
        orders.setPayAmount(new BigDecimal(amount));
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());

        ordersMapper.insert(orders);

        AccountRequest request = new AccountRequest();
        request.setUserId(id);
        request.setAmount(amount);
        AccountResponse response = bankClient.transfer(request);
        log.info("response:{}", JSON.toJSONString(response));
        return true;
    }
}
