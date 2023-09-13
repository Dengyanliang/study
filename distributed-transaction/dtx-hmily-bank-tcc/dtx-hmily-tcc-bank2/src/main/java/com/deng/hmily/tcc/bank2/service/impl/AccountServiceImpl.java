package com.deng.hmily.tcc.bank2.service.impl;

import com.deng.hmily.tcc.bank2.dao.mapper.AccountMapper;
import com.deng.hmily.tcc.bank2.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.hmily.tcc.bank2.dao.po.Account;
import com.deng.hmily.tcc.bank2.dao.po.TccLocalConfirmLog;
import com.deng.hmily.tcc.bank2.facade.request.Bank2AccountRequest;
import com.deng.hmily.tcc.bank2.service.AccountService;
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
    private TccLocalConfirmLogMapper tccLocalConfirmLogMapper;


    @Override
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    public void tryFreezeAmount(Bank2AccountRequest request) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account try 开始执行 什么也不用做，txNo:{}",txNo);


        log.info("account try 执行结束 什么也不用做，txNo:{}",txNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void commit(Bank2AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account confirm 开始执行，txNo:{}",txNo);

        // confirm实现幂等
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo))){
            log.info("account confirm 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        TccLocalConfirmLog confirmLog = new TccLocalConfirmLog();
        confirmLog.setTxNo(txNo);
        confirmLog.setCreateTime(new Date());
        tccLocalConfirmLogMapper.insert(confirmLog);

        Account dbAccount = getAccount(request,txNo);

        // 余额=余额-amount
        int count = accountMapper.increaseAccountAmountById(dbAccount.getId(),request.getAmount());
        if(count <= 0){
            throw new RuntimeException("扣减余额和冻结金额失败");
        }

        log.info("account confirm 执行结束，txNo:{}",txNo);
    }

    public void rollback(Bank2AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account cancel 开始执行 什么也不用做，txNo:{}",txNo);

        log.info("account cancel 执行结束 什么也不用做，txNo:{}",txNo);
    }

    private Account getAccount(Bank2AccountRequest request, String txNo){
        Account account = accountMapper.selectByPrimaryKey(request.getUserId());
        if (Objects.isNull(account)) {
            log.info("账户余额为空，txNo:{}",txNo);
            throw new RuntimeException("账户为空");
        }
        return account;
    }

}
