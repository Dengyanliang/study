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
package com.deng.seata.saga.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.seata.saga.account.facade.BalanceAction;
import com.deng.seata.saga.account.facade.request.BalanceInfo;
import com.deng.seata.saga.account.facade.request.BalanceRequest;
import com.deng.seata.saga.order.service.InventoryAction;
import com.google.common.collect.Maps;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lorne.cl
 */
@Slf4j
@Service("inventoryAction")
public class InventoryActionImpl implements InventoryAction {

    @Autowired
    private BalanceAction balanceAction;

    @Autowired
    private ProcessCtrlStateMachineEngine stateMachineEngine;

    public boolean execute(String businessKey, int count){
        log.info("******** InventoryAction execute, count: {}, businessKey: {}", count, businessKey);
        Map<String, Object> startParams = new HashMap<>(3);
        startParams.put("businessKey", businessKey);
        startParams.put("count", count);
        startParams.put("amount", new BigDecimal(count));

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
//        balanceRequest.setBalanceInfoArray(balanceInfoArray);
//        balanceRequest.setBalanceInfoList(balanceInfoList);
//        balanceRequest.setBalanceInfoMap(balanceInfoMap);

        startParams.put("balanceRequest",balanceRequest);
        startParams.put("balanceInfo1",balanceInfo1);
        startParams.put("balanceInfo2",balanceInfo2);


        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("reduceInventoryAndBalance", null, businessKey, startParams);
        System.out.println("........" + JSON.toJSONString(inst));
        if (ExecutionStatus.SU.equals(inst.getStatus())) {
            log.info("saga transaction commit succeed. XID: {}", inst.getId());
            inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
            log.info("》》》》saga transaction execute over. XID: {}, status: {}, compensationStatus: {}", inst.getId(), inst.getStatus(), inst.getCompensationStatus());
            // 防止空悬挂和空回滚
            if (ExecutionStatus.SU.equals(inst.getStatus()) || ExecutionStatus.SU.equals(inst.getCompensationStatus())) {
                return true;
            }

            return false;
        }else{
            inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
            log.info(">>>> saga transaction execute over. XID: {}, status: {}, compensationStatus: {}", inst.getId(), inst.getStatus(), inst.getCompensationStatus());
            // 防止空悬挂和空回滚
            if (ExecutionStatus.SU.equals(inst.getStatus()) || ExecutionStatus.SU.equals(inst.getCompensationStatus())) {
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean reduce(String businessKey, int count) {
        log.info("******** reduce inventory succeed, count: {}, businessKey: {}", count, businessKey);
        return true;
    }

    @Override
    public boolean compensateReduce(String businessKey) {
        log.info("******** compensate reduce inventory succeed, businessKey: {}", businessKey);
        return true;
    }
}