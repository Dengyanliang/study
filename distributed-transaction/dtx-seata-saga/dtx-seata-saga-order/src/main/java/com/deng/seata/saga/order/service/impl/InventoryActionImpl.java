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

import com.deng.seata.saga.account.facade.BalanceAction;
import com.deng.seata.saga.order.service.InventoryAction;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
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
//        startParams.put("businessKey", "1234");
        startParams.put("count", count);
//        startParams.put("amount", new BigDecimal(count));

        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("reduceInventoryAndBalance", null, businessKey, startParams);
        System.out.println("........" + inst.getStatus());
        if (ExecutionStatus.SU.equals(inst.getStatus())) {
            log.info("saga transaction commit succeed. XID: {}", inst.getId());
            inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
            if (ExecutionStatus.SU.equals(inst.getStatus())) {
                return true;
            }
            log.info("saga transaction execute failed. XID:{},status:{}", inst.getId(), inst.getStatus());
            return false;
        }else{
            log.info("===================================");
            return false;
        }
    }

    @Override
    public boolean reduce(String businessKey, int count) {
        log.info("******** reduce inventory succeed, count: {}, businessKey: {}", count, businessKey);

        log.info(">>> begin dubbo invoke");
        balanceAction.reduce("123",new BigDecimal(count),null);
        log.info(">>> end dubbo invoke");
//        try{
//            int i =  10 / 0;
//        }catch(Exception e){
//            return false;
//        }

        return true;
    }

    @Override
    public boolean compensateReduce(String businessKey) {
        log.info("******** compensate reduce inventory succeed, businessKey: {}", businessKey);
        return true;
    }
}