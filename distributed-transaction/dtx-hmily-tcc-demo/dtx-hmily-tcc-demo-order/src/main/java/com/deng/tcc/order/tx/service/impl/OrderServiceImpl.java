package com.deng.tcc.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.tcc.order.tx.dao.mapper.OrdersMapper;
import com.deng.tcc.order.tx.dao.po.Orders;
import com.deng.tcc.order.tx.remote.client.AccountClient;
import com.deng.tcc.order.tx.remote.request.AccountRequest;
import com.deng.tcc.order.tx.remote.response.AccountResponse;
import com.deng.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AccountClient accountClient;

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    @Override
    @Hmily(confirmMethod="commit",cancelMethod = "rollback")
    public boolean addOrder(Integer id, Long amount) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();

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

    @Transactional
    public void commit(Integer userId,Long amount){
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("order confirm begin, xid:{},accountNo:{},amount:{}",transId,userId,amount);
    }


    @Transactional
    public void rollback(Integer userId,Long amount){
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("order cancel begin ,xid:{}",transId);

        // cancel幂等校验
//        if(){
//
//        }

        // cancel空回滚处理，如果try没有执行，cancel不允许执行




    }

}
