package com.deng.seata.tcc.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.tcc.order.dao.mapper.OrdersMapper;
import com.deng.seata.tcc.order.dao.po.Orders;
import com.deng.seata.tcc.order.facade.request.OrderRequest;
import com.deng.seata.tcc.order.service.OrderService;
import com.deng.seata.tcc.storage.facade.StockFacade;
import com.deng.seata.tcc.storage.facade.request.StockRequest;
import com.deng.seata.tcc.storage.facade.response.StockResponse;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

//    @Autowired
//    private AccountClient accountClient;

    @Autowired
    private StockFacade stockFacade;

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

//        // 余额操作
//        AccountRequest accountRequest = new AccountRequest();
//        accountRequest.setUserId(orderRequest.getUserId());
//        accountRequest.setAmount(orderRequest.getAmount());
//        log.info("accountRequest:{}", JSON.toJSONString(accountRequest));
//        AccountResponse accountResponse = accountClient.transfer(accountRequest);
//        log.info("accountResponse:{}", JSON.toJSONString(accountResponse));

        // 库存操作
        StockRequest stockRequest = new StockRequest();
        stockRequest.setProductId(orderRequest.getProductId());
        stockRequest.setCount(orderRequest.getCount());
        log.info("stockRequest:{}", JSON.toJSONString(stockRequest));
        StockResponse stockResponse = stockFacade.tryFreezeStock(stockRequest);
        log.info("stockResponse:{}", JSON.toJSONString(stockResponse));

//        int i =  10 / 0;
    }
}
