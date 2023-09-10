package com.deng.hmily.tcc.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.PayStatusEnum;
import com.deng.common.enums.ResponseEnum;
import com.deng.hmily.tcc.order.tx.dao.mapper.OrdersMapper;
import com.deng.hmily.tcc.order.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.hmily.tcc.order.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.hmily.tcc.order.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.hmily.tcc.order.tx.dao.po.Orders;
import com.deng.hmily.tcc.order.tx.dao.po.TccLocalCancelLog;
import com.deng.hmily.tcc.order.tx.dao.po.TccLocalConfirmLog;
import com.deng.hmily.tcc.order.tx.dao.po.TccLocalTryLog;
import com.deng.hmily.tcc.order.tx.facade.request.OrderRequest;
import com.deng.hmily.tcc.order.tx.remote.client.AccountClient;
import com.deng.hmily.tcc.order.tx.remote.client.StorageClient;
import com.deng.hmily.tcc.order.tx.remote.request.AccountRequest;
import com.deng.hmily.tcc.order.tx.remote.request.StorageRequest;
import com.deng.hmily.tcc.order.tx.remote.response.AccountResponse;
import com.deng.hmily.tcc.order.tx.remote.response.StorageResponse;
import com.deng.hmily.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StorageClient storageClient;

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
    @Transactional(rollbackFor = Exception.class)
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
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUserId(order.getUserId());
        accountRequest.setAmount(order.getPayAmount());
        AccountResponse accountResponse = accountClient.tryFreezeAmount(accountRequest);
        log.info("accountResponse:{}", JSON.toJSONString(accountResponse));
        if(accountResponse.getCode() != ResponseEnum.SUCCESS.getCode()){
            throw new RuntimeException("远程调用失败");
        }

        StorageRequest storageRequest = new StorageRequest();
        storageRequest.setProductId(order.getProductId());
        storageRequest.setCount(order.getCount());
        StorageResponse storageResponse = storageClient.tryFreezeStock(storageRequest);

        if(storageResponse.getCode() != ResponseEnum.SUCCESS.getCode()){
            throw new RuntimeException("远程调用失败");
        }

        // 为了测试异常，当调用方调用其他微服务try方法成功之后，后面代码发生了异常，那么就会调用其他微服务的cancel方法
//        int i = 10 / 0;

        log.info("addOrder try 执行结束，txNo:{} ",txNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitAddOrder(Orders order){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder confirm 开始执行, txNo:{},accountNo:{},amount:{}",txNo,order.getUserId(),order.getPayAmount());

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

        // cancel幂等校验
        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空提交处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
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
        orders.setPayAmount(orderRequest.getAmount());
        orders.setPayStatus(PayStatusEnum.INIT.getCode());
        orders.setCount(orderRequest.getCount());
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());
        int addCount = ordersMapper.insert(orders);
        if(addCount <= 0){
            throw new RuntimeException("添加订单失败");
        }
        return orders;
    }

    private void updateOrderPayStatus(Long orderId, PayStatusEnum payStatus){
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setPayStatus(payStatus.getCode());
        int updateCount = ordersMapper.updateByPrimaryKeySelective(orders);
        if(updateCount <= 0){
            throw new RuntimeException("更新订单状态失败");
        }
    }
}
