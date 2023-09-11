package com.deng.seata.account.tx.service.impl;

import com.deng.seata.account.tx.dao.mapper.AccountMapper;
import com.deng.seata.account.tx.dao.po.Account;
import com.deng.seata.account.tx.facade.request.AccountRequest;
import com.deng.seata.account.tx.service.AccountService;
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
    @Transactional(rollbackFor = Exception.class)
    public void transfer(AccountRequest request) {

        Account dbAccount = accountMapper.selectByPrimaryKey(request.getUserId());
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(request.getUserId());
        // 这种写法不好，要使用数据库的update set 才能保证数据的正确性
        account.setBalance(dbAccount.getBalance()-request.getAmount().doubleValue());
        account.setLastUpdateTime(new Date());

        int count = accountMapper.updateByPrimaryKeySelective(account);
        if(count <= 0){
            throw new RuntimeException("转账失败！");
        }
    }
}
