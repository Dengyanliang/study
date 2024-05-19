package com.deng.seata.tcc.account.service.impl;

import com.deng.seata.tcc.account.dao.mapper.AccountMapper;
import com.deng.seata.tcc.account.dao.po.Account;
import com.deng.seata.tcc.account.facade.request.AccountRequest;
import com.deng.seata.tcc.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(AccountRequest request) {
        Account dbAccount = accountMapper.selectById(request.getUserId());
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(request.getUserId());
        account.setFreezeAmount(account.getFreezeAmount() + request.getAmount()); // 增加冻结金额

        int count = accountMapper.updateById(account);
        if(count <= 0){
            throw new RuntimeException("转账失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferCommit(AccountRequest request) {
        Account dbAccount = accountMapper.selectById(request.getUserId());
        if (Objects.isNull(dbAccount)) {
            throw new RuntimeException("账户为空");
        }

        dbAccount.setFreezeAmount(dbAccount.getFreezeAmount()-request.getAmount()); // 扣除冻结金额
        dbAccount.setBalance(dbAccount.getBalance()-request.getAmount());

        int count = accountMapper.updateById(dbAccount);
        if(count <= 0){
            throw new RuntimeException("转账失败！");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferCancel(AccountRequest request) {
        Account dbAccount = accountMapper.selectById(request.getUserId());
        if (Objects.isNull(dbAccount)) {
            throw new RuntimeException("账户为空");
        }

        dbAccount.setFreezeAmount(dbAccount.getFreezeAmount()-request.getAmount()); // 扣除冻结金额
        int count = accountMapper.updateById(dbAccount);
        if(count <= 0){
            throw new RuntimeException("转账失败！");
        }

    }
}
