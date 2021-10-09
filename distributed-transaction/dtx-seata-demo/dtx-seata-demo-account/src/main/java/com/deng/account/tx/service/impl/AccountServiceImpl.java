package com.deng.account.tx.service.impl;

import com.deng.account.tx.facade.request.AccountRequest;
import com.deng.account.tx.service.AccountService;
import com.deng.account.tx.dao.po.Account;
import com.deng.account.tx.dao.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public boolean transfer(AccountRequest request) {

        Account dbAccount = accountMapper.selectByPrimaryKey(request.getUserId());
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(request.getUserId());
        account.setBalance(dbAccount.getBalance()-request.getAmount().doubleValue());
        account.setLastUpdateTime(new Date());

        int count = accountMapper.updateByPrimaryKey(account);

        return count > 0;
    }
}
