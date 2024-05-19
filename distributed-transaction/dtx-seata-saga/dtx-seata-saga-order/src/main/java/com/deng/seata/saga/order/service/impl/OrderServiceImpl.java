package com.deng.seata.saga.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.seata.saga.account.facade.request.BalanceInfo;
import com.deng.seata.saga.account.facade.request.BalanceRequest;
import com.deng.seata.saga.order.dao.mapper.OrdersMapper;
import com.deng.seata.saga.order.dao.po.Orders;
import com.deng.seata.saga.order.facade.request.OrderRequest;
import com.deng.seata.saga.order.service.OrderService;
import com.google.common.collect.Maps;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProcessCtrlStateMachineEngine stateMachineEngine;

    @Resource
    private OrdersMapper ordersMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderRequest orderRequest) {
        log.info("addOrder 开始......");

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

        // 执行状态机
        boolean result = executeStateMachineEngine("12323",orderRequest.getCount());
        if(result){
            log.info("下单成功........");
        }else{
            log.info("下单成功，删除订单........");
            int count = ordersMapper.deleteById(orders);
            log.info("删除结果：{}", count);
        }
    }

    /**
     * 执行状态机流程：锁库存、冻结余额
     * @param businessKey 自定义的上下文流水号
     * @param count
     * @return
     */
    public boolean executeStateMachineEngine(String businessKey, Long count){
        log.info("******** InventoryAction execute, count: {}, businessKey: {}", count, businessKey);
        Map<String, Object> startParams = new HashMap<>(3);
        startParams.put("businessKey", businessKey);
        startParams.put("count", count);
        startParams.put("amount", count);

        BalanceInfo balanceInfo1 = new BalanceInfo();
        balanceInfo1.setName("zhangsan");
        balanceInfo1.setAmount(count * 10);

        BalanceInfo balanceInfo2 = new BalanceInfo();
        balanceInfo2.setName("lisi");
        balanceInfo2.setAmount(count * 20);

        BalanceInfo[] balanceInfoArray = new BalanceInfo[]{balanceInfo1,balanceInfo2};
        List<BalanceInfo> balanceInfoList = Lists.newArrayList(balanceInfo1,balanceInfo2);
        Map<String,BalanceInfo> balanceInfoMap = Maps.newHashMap();
        balanceInfoMap.put("balanceKey1",balanceInfo1);
        balanceInfoMap.put("balanceKey2",balanceInfo2);

        BalanceRequest balanceRequest = new BalanceRequest();
        balanceRequest.setOrderNo("orderNo-123");
        balanceRequest.setUserId(123);
        balanceRequest.setBalanceInfoArray(balanceInfoArray);
        balanceRequest.setBalanceInfoList(balanceInfoList);
        balanceRequest.setBalanceInfoMap(balanceInfoMap);

        startParams.put("balanceRequest",balanceRequest);
        startParams.put("balanceInfo1",balanceInfo1);
        startParams.put("balanceInfo2",balanceInfo2);

        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("reduceInventoryAndBalance", null, businessKey, startParams);
        System.out.println("........" + JSON.toJSONString(inst));
        if (ExecutionStatus.SU.equals(inst.getStatus())) {
            log.info("saga transaction commit succeed. XID: {}", inst.getId());
            inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
            log.info("》》》》saga transaction execute over. XID: {}, status: {}, compensationStatus: {}", inst.getId(), inst.getStatus(), inst.getCompensationStatus());
            return true;
        }else{
            return false;
        }
    }
}
