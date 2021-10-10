package com.deng.tcc.account.tx.service.impl;

import com.deng.tcc.account.tx.dao.mapper.AccountMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.tcc.account.tx.dao.po.Account;
import com.deng.tcc.account.tx.dao.po.TccLocalConfirmLog;
import com.deng.tcc.account.tx.facade.request.AccountRequest;
import com.deng.tcc.account.tx.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
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
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private TccLocalConfirmLogMapper localConfirmLogMapper;

    @Override
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    public void updateBalance(AccountRequest request) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account try 开始执行，txNo:{}",txNo);
    }

    @Transactional
    public void commit(AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account confirm 开始执行，txNo:{}",txNo);

        if(Objects.nonNull(localConfirmLogMapper.selectByPrimaryKey(txNo))){
            log.info("account confirm 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        Account dbAccount = accountMapper.selectByPrimaryKey(request.getUserId());
        if (Objects.isNull(dbAccount) || dbAccount.getBalance() < request.getAmount()) {
            log.info("账户余额为空，txNo:{}",txNo);
            throw new RuntimeException("账户为空或者余额不足");
        }

        Account account = new Account();
        account.setId(request.getUserId());
        account.setBalance(dbAccount.getBalance()-request.getAmount().doubleValue());
        account.setLastUpdateTime(new Date());

        // 更新余额
        int count = accountMapper.updateByPrimaryKey(account);
        if(count <= 0){
            throw new RuntimeException("更新金额失败");
        }

        // 添加confirm日志
        TccLocalConfirmLog localConfirmLog = new TccLocalConfirmLog();
        localConfirmLog.setTxNo(txNo);
        localConfirmLog.setCreateTime(new Date());
        localConfirmLogMapper.insert(localConfirmLog);

        log.info("account confirm 执行结束，txNo:{}",txNo);
    }

    public void rollback(AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account cancel 开始执行，txNo:{}",txNo);
    }

}
