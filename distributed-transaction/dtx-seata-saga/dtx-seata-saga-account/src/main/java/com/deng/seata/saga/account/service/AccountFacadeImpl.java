package com.deng.seata.saga.account.service;

import com.deng.seata.saga.account.facade.AccountFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2024/5/9 15:11
 * @Auther: dengyanliang
 */
@Slf4j
@Component
public class AccountFacadeImpl implements AccountFacade {

    @Override
    public boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params) {
        if(params != null) {
            Object throwException = params.get("throwException");
            if (throwException != null && "true".equals(throwException.toString())) {
                throw new RuntimeException("reduce balance failed");
            }
        }
        log.info(">>>reduce balance succeed, amount: " + amount + ", businessKey:" + businessKey);
        return true;
    }

    @Override
    public boolean compensateReduce(String businessKey, Map<String, Object> params) {
        if(params != null) {
            Object throwException = params.get("throwException");
            if (throwException != null && "true".equals(throwException.toString())) {
                throw new RuntimeException("compensate reduce balance failed");
            }
        }
        log.info(">>>compensate reduce balance succeed, businessKey:" + businessKey);
        return true;
    }
}
