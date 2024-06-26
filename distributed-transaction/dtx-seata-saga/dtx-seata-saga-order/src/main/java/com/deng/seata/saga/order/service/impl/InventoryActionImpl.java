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

import com.deng.seata.saga.order.service.InventoryAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service("inventoryAction")
public class InventoryActionImpl implements InventoryAction {

    @Override
    public boolean reduce(String businessKey, Long count) {
        log.info("******** reduce inventory succeed, count: {}, businessKey: {}", count, businessKey);
        return true;
    }

    @Override
    public boolean compensateReduce(String businessKey) {
        log.info("******** compensate reduce inventory succeed, businessKey: {}", businessKey);
        return true;
    }
}