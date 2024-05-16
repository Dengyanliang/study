/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.deng.seata.saga.account.facade;

import com.deng.seata.saga.account.facade.request.BalanceInfo;
import com.deng.seata.saga.account.facade.request.BalanceRequest;
import com.deng.seata.saga.account.facade.response.BalanceResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Balance Actions
 */
public interface BalanceAction {

    /**
     * 简单参数正向方法
     */
    boolean reduce(String businessKey, BigDecimal amount, Map<String, Object> params);

    /**
     * 简单参数逆向方法
     */
    boolean compensateReduce(String businessKey, Map<String, Object> params);

    /**
     * 复杂参数正向方法
     */
    BalanceResponse reduceComplex1(String businessKey, BalanceRequest balanceRequest);


    /**
     * 复杂参数正向方法
     */
    BalanceResponse reduceComplex2(String businessKey, BalanceRequest balanceRequest, BalanceInfo[] balanceInfoArray, List<BalanceInfo> balanceInfoList, Map<String,BalanceInfo> balanceInfoMap);

    /**
     * 复杂参数逆向方法
     */
    BalanceResponse compensateReduceComplex(String businessKey, BalanceRequest balanceRequest);

}
