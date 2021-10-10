package com.deng.tcc.order.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.tcc.order.tx.dao.mapper.OrdersMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.tcc.order.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.tcc.order.tx.dao.po.Orders;
import com.deng.tcc.order.tx.dao.po.TccLocalCancelLog;
import com.deng.tcc.order.tx.dao.po.TccLocalConfirmLog;
import com.deng.tcc.order.tx.dao.po.TccLocalTryLog;
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
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AccountClient accountClient;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private TccLocalTryLogMapper localTryLogMapper;

    @Resource
    private TccLocalConfirmLogMapper localConfirmLogMapper;

    @Resource
    private TccLocalCancelLogMapper localCancelLogMapper;

    @Transactional
    @Override
    @Hmily(confirmMethod="commit",cancelMethod = "rollback")
    public void addOrder(Integer userId, Long amount) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder try 开始执行，txNo:{}",txNo);

        // 幂等判断，判断tcc_local_try_log中是否有记录，如果有则不再执行
        if(Objects.nonNull(localTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder try 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行
        if(Objects.nonNull(localConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(localCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
            return;
        }

        // 新增订单
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setProductId(2);
        orders.setPayAmount(new BigDecimal(amount));
        orders.setAddTime(new Date());
        orders.setLastUpdateTime(new Date());
        int addCount = ordersMapper.insert(orders);
        if(addCount <= 0){
            throw new RuntimeException("添加订单失败");
        }

        // 增加try记录
        TccLocalTryLog localTryLog = new TccLocalTryLog();
        localTryLog.setTxNo(txNo);
        localTryLog.setCreateTime(new Date());
        localTryLogMapper.insert(localTryLog);

        // 远程调用
        AccountRequest request = new AccountRequest();
        request.setUserId(userId);
        request.setAmount(amount);
        AccountResponse response = accountClient.updateBalance(request);
        log.info("response:{}", JSON.toJSONString(response));

        if(response.getCode() != 0){
            throw new RuntimeException("远程调用失败");
        }
        log.info("addOrder try 执行结束，txNo:{} ",txNo);
    }

    public void commit(Integer userId,Long amount){
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder confirm 开始执行, txNo:{},accountNo:{},amount:{}",transId,userId,amount);
    }

    @Transactional
    public void rollback(Integer userId,Long amount){
        // 获取全局事务id
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("addOrder cancel 开始执行 ,txNo:{}",txNo);

        // cancel幂等校验
        if(Objects.nonNull(localCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(localTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空回滚处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
            return;
        }

        // 删除订单
        log.info("删除订单，可以通过txNo：{}关联处理，",txNo);

        // 插入一条cancel记录
        TccLocalCancelLog localCancelLog = new TccLocalCancelLog();
        localCancelLog.setTxNo(txNo);
        localCancelLog.setCreateTime(new Date());
        localCancelLogMapper.insert(localCancelLog);

        log.info("addOrder cancel 执行结束 ,txNo:{}",txNo);
    }

}
