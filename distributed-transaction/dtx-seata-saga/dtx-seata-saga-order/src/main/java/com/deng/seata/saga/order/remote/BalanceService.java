package com.deng.seata.saga.order.remote;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Desc:
 * @Date: 2024/5/15 20:43
 * @Auther: dengyanliang
 */
public interface BalanceService {
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
