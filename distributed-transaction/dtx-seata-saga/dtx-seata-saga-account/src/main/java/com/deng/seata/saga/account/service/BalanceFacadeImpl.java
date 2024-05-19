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
package com.deng.seata.saga.account.service;

import com.alibaba.fastjson.JSON;
import com.deng.seata.saga.account.dao.mapper.AccountMapper;
import com.deng.seata.saga.account.dao.po.Account;
import com.deng.seata.saga.account.facade.BalanceFacade;
import com.deng.seata.saga.account.facade.request.BalanceInfo;
import com.deng.seata.saga.account.facade.request.BalanceRequest;
import com.deng.seata.saga.account.facade.response.BalanceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service("balanceFacade")
public class BalanceFacadeImpl implements BalanceFacade {

    @Resource
    private AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reduce(String businessKey, Long amount, Map<String, Object> params) {
        log.info(">>>执行 balanceAction reduce succeed, businessKey:{}, amount: {}, params: {}", businessKey, amount, params);
        int accountId = 2;
        Account dbAccount = accountMapper.selectById(accountId);
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < amount) {
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(accountId);
        account.setFreezeAmount(amount); // 增加冻结金额

        int count = accountMapper.updateById(account);
        if(count <= 0){
            throw new RuntimeException("增加冻结金额失败！");
        }

        int i = 10 / 0;

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean compensateReduce(String businessKey, Long amount, Map<String, Object> params) {
        log.info(">>>执行 balanceAction compensateReduce succeed, businessKey:{}, amount: {}, params: {}", businessKey, amount, params);
        int accountId = 2;
        Account dbAccount = accountMapper.selectById(accountId);
        if (Objects.isNull(dbAccount)) {
            throw new RuntimeException("账户为空");
        }

        dbAccount.setFreezeAmount(dbAccount.getFreezeAmount()-amount); // 扣除冻结金额
        int count = accountMapper.updateById(dbAccount);
        if(count <= 0){
            throw new RuntimeException("扣除冻结金额失败！");
        }

        return true;
    }


    @Override
    public BalanceResponse reduceComplex1(String businessKey, BalanceRequest balanceRequest) {
        log.info(">>> 执行 balanceAction reduceComplex1 succeed, businessKey: {},balanceRequest: {}",
                businessKey, JSON.toJSONString(balanceRequest));
        BalanceResponse response = new BalanceResponse();
        response.setCode("1000");
        response.setMsg("成功");
        return response;
    }

    @Override
    public BalanceResponse reduceComplex2(String businessKey, BalanceRequest balanceRequest,
                                         BalanceInfo[] balanceInfoArray,
                                         List<BalanceInfo> balanceInfoList,
                                         Map<String,BalanceInfo> balanceInfoMap) {
        log.info(">>> 执行 balanceAction reduceComplex2 succeed, businessKey: {},balanceRequest: {},balanceInfoArray: {},balanceInfoList: {},balanceInfoMap: {}",
                            businessKey, JSON.toJSONString(balanceRequest), balanceInfoArray, balanceInfoList, balanceInfoMap);
        BalanceResponse response = new BalanceResponse();
        response.setCode("1000");
        response.setMsg("成功");

//        response.setCode("2000");
//        response.setMsg("失败");
//        int i = 10 / 0;
        return response;
    }

    @Override
    public BalanceResponse compensateReduceComplex(String businessKey, BalanceRequest balanceRequest) {
        log.info(">>> 执行 balanceAction compensateReduceComplex succeed, businessKey: {},balanceRequest: {}", businessKey, JSON.toJSONString(balanceRequest));

        BalanceResponse response = new BalanceResponse();
        response.setCode("1000");
        response.setMsg("成功");
        return response;
    }
}