package com.deng.bank2.tx.service.impl;

import com.deng.bank2.tx.entity.Account;
import com.deng.bank2.tx.facade.request.AccountRequest;
import com.deng.bank2.tx.mapper.AccountMapper;
import com.deng.bank2.tx.service.AccountService;
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
        if(Objects.isNull(dbAccount)){
            return false;
        }

        Account account = new Account();
        account.setId(request.getUserId());
        account.setBalance(dbAccount.getBalance()-request.getAmount().doubleValue());
        account.setLastUpdateTime(new Date());

        int count = accountMapper.updateByPrimaryKey(account);

        return count > 0;
    }
}
