package com.deng.seata.saga.account.facade;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2024/5/9 15:10
 * @Auther: dengyanliang
 */
public interface AccountFacade {
    /**
     * reduce
     *
     * @param businessKey
     * @param amount
     * @param params
     * @return
     */
    boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params);

    /**
     * compensateReduce
     *
     * @param businessKey
     * @param params
     * @return
     */
    boolean compensateReduce(String businessKey, Map<String, Object> params);
}
