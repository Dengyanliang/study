package com.deng.seata.dynamic.ds.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deng.seata.dynamic.ds.dao.mapper.AccountMapper;
import com.deng.seata.dynamic.ds.dao.po.Account;
import com.deng.seata.dynamic.ds.service.AccountService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    @DS("account-ds")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deductAmount(Integer userId, Long amount) {

        String txNo = RootContext.getXID();
        log.info("deductAmount 开始执行，事务ID------>{}",txNo);

        Account dbAccount = accountMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < amount) {
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(userId);
        account.setBalance(dbAccount.getBalance()-amount);
        int count = accountMapper.updateByPrimaryKeySelective(account);
        if(count <= 0){
            throw new RuntimeException("扣减余额失败！");
        }

        log.info("deductAmount 执行结束，txNo:{}",txNo);
    }
}
