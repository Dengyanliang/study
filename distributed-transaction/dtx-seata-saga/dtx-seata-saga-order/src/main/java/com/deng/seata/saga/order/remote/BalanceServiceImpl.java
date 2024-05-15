package com.deng.seata.saga.order.remote;

import com.deng.seata.saga.account.facade.BalanceAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2024/5/15 20:43
 * @Auther: dengyanliang
 */
@Slf4j
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService{

    @Autowired
    private BalanceAction balanceAction;

    @Override
    public boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params) {
        log.info("》》》》balanceService执行reduce开始");
        return balanceAction.reduce(businessKey,amount,params);
    }

    @Override
    public boolean compensateReduce(String businessKey, Map<String, Object> params) {
        log.info(">>>> balanceService执行compensateReduce开始");
        return balanceAction.compensateReduce(businessKey,params);
    }
}
