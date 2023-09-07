package com.deng.tcc.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.tcc.order.tx.dao.mapper.OrdersMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.tcc.order.tx.dao.po.Orders;
import com.deng.tcc.order.tx.dao.po.TccLocalCancelLog;
import com.deng.tcc.order.tx.dao.po.TccLocalConfirmLog;
import com.deng.tcc.order.tx.dao.po.TccLocalTryLog;
import com.deng.tcc.order.tx.facade.request.OrderRequest;
import com.deng.tcc.order.tx.remote.client.AccountClient;
import com.deng.tcc.order.tx.remote.request.AccountRequest;
import com.deng.tcc.order.tx.remote.response.AccountResponse;
import com.deng.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private AccountClient accountClient;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private TccLocalTryLogMapper tccLocalTryLogMapper;

    @Resource
    private TccLocalConfirmLogMapper tccLocalConfirmLogMapper;

    @Resource
    private TccLocalCancelLogMapper tccLocalCancelLogMapper;

    @Resource
    private OrderService orderService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderRequest orderRequest) {
        Orders orders = saveOrder(orderRequest);
        orderService.tryAddOrder(orders);
    }


    @Hmily(confirmMethod="commitAddOrder",cancelMethod = "rollbackAddOrder")
//    @Transactional(rollbackFor = Exception.class)
    public void tryAddOrder(Orders order) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder try 开始执行，txNo:{}",txNo);

        // 幂等判断，判断tcc_local_try_log中是否有记录，如果有则不再执行
        if(Objects.nonNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder try 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
            return;
        }

        // 增加try记录
        TccLocalTryLog tryLog = new TccLocalTryLog();
        tryLog.setTxNo(txNo);
        tryLog.setCreateTime(new Date());
        tccLocalTryLogMapper.insert(tryLog);

        // 远程调用
        AccountRequest request = new AccountRequest();
        request.setUserId(order.getUserId());
        request.setAmount(order.getPayAmount().longValue());
        AccountResponse response = accountClient.tryFreezeAmount(request);
        log.info("response:{}", JSON.toJSONString(response));

        if(response.getCode() != 0){
            throw new RuntimeException("远程调用失败");
        }

        log.info("addOrder try 执行结束，txNo:{} ",txNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitAddOrder(Orders order){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder confirm 开始执行, txNo:{},accountNo:{},amount:{}",txNo,order.getUserId(),order.getPayAmount());

        // confirm空回滚处理，如果try没有执行，confirm不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空提交处理，try没有执行，不允许confirm执行，txNo:{}",txNo);
            return;
        }

        // confirm幂等校验
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder confirm 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        TccLocalConfirmLog confirmLog = new TccLocalConfirmLog();
        confirmLog.setTxNo(txNo);
        confirmLog.setCreateTime(new Date());
        tccLocalConfirmLogMapper.insert(confirmLog);

        // 更新订单为支付中
        updateOrderPayStatus(order.getId(),PayStatusEnum.PROCESSING);


        log.info("addOrder confirm 执行结束, txNo:{},accountNo:{},amount:{}",txNo,order.getUserId(),order.getPayAmount());
    }

    @Transactional(rollbackFor = Exception.class)
    public void rollbackAddOrder(Orders order){
        // 获取全局事务id
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder cancel 开始执行 ,txNo:{}",txNo);

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空提交处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
            return;
        }

        // cancel幂等校验
        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        // 插入一条cancel记录
        TccLocalCancelLog localCancelLog = new TccLocalCancelLog();
        localCancelLog.setTxNo(txNo);
        localCancelLog.setCreateTime(new Date());
        tccLocalCancelLogMapper.insert(localCancelLog);

        // 这里应该是增加金额
        log.info("更新订单状态为关闭，txNo：{}，",txNo);

        updateOrderPayStatus(order.getId(),PayStatusEnum.CLOSED);

        log.info("addOrder cancel 执行结束 ,txNo:{}",txNo);
    }

    private Orders saveOrder(OrderRequest orderRequest){
        // 新增订单
        Orders orders = new Orders();
        orders.setUserId(orderRequest.getUserId());
        orders.setProductId(orderRequest.getProductId());
        orders.setPayAmount(new BigDecimal(orderRequest.getAmount()));
        orders.setPayStatus(PayStatusEnum.INIT.getCode());
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());
        int addCount = ordersMapper.insert(orders);
        if(addCount <= 0){
            throw new RuntimeException("添加订单失败");
        }
        return orders;
    }

    private void updateOrderPayStatus(Integer orderId, PayStatusEnum payStatus){
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setPayStatus(payStatus.getCode());
        int updateCount = ordersMapper.updateByPrimaryKey(orders);
        if(updateCount <= 0){
            throw new RuntimeException("更新订单状态失败");
        }
    }
}
