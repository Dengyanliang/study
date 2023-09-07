package com.deng.tcc.account.tx.service.impl;

import com.deng.tcc.account.tx.dao.mapper.AccountMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.tcc.account.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.tcc.account.tx.dao.po.Account;
import com.deng.tcc.account.tx.dao.po.TccLocalCancelLog;
import com.deng.tcc.account.tx.dao.po.TccLocalConfirmLog;
import com.deng.tcc.account.tx.dao.po.TccLocalTryLog;
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
    private TccLocalTryLogMapper tccLocalTryLogMapper;

    @Resource
    private TccLocalConfirmLogMapper tccLocalConfirmLogMapper;

    @Resource
    private TccLocalCancelLogMapper tccLocalCancelLogMapper;

    @Override
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    @Transactional(rollbackFor = Exception.class)
    public void tryFreezeAmount(AccountRequest request) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account try 开始执行，txNo:{}",txNo);

        if(Objects.nonNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("account try 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
            return;
        }

        // 添加try日志
        TccLocalTryLog tryLog = new TccLocalTryLog();
        tryLog.setTxNo(txNo);
        tryLog.setCreateTime(new Date());
        tccLocalTryLogMapper.insert(tryLog);

        Account dbAccount = getAccount(request,txNo);

        // try 冻结金额=冻结金额+amount
        int count = accountMapper.addFreezeAmountAndCheckBalanceById(dbAccount.getId(),request.getAmount());
        if(count <= 0){
            throw new RuntimeException("增加冻结金额失败");
        }

        log.info("account try 执行结束，txNo:{}",txNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void commit(AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account confirm 开始执行，txNo:{}",txNo);

        // confirm空回滚处理，如果try没有执行，confirm不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空提交处理，try没有执行，不允许confirm执行，txNo:{}",txNo);
            return;
        }

        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo))){
            log.info("account confirm 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        TccLocalConfirmLog confirmLog = new TccLocalConfirmLog();
        confirmLog.setTxNo(txNo);
        confirmLog.setCreateTime(new Date());
        tccLocalConfirmLogMapper.insert(confirmLog);

        Account dbAccount = getAccount(request,txNo);

        // confirm 冻结金额=冻结金额-amount，余额=余额-amount
        int count = accountMapper.deductBalanceAndFreezeAmountById(dbAccount.getId(),request.getAmount());
        if(count <= 0){
            throw new RuntimeException("扣减余额和冻结金额失败");
        }

        log.info("account confirm 执行结束，txNo:{}",txNo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void rollback(AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account cancel 开始执行，txNo:{}",txNo);

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder 空回滚处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
            return;
        }

        // cancel幂等校验
        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("addOrder cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        TccLocalCancelLog cancelLog = new TccLocalCancelLog();
        cancelLog.setTxNo(txNo);
        cancelLog.setCreateTime(new Date());
        tccLocalCancelLogMapper.insert(cancelLog);


        // cancel  冻结金额=冻结金额-amount
        Account dbAccount = getAccount(request,txNo);

        // try 冻结金额=冻结金额+amount
        int count = accountMapper.deductFreezeAmountById(dbAccount.getId(),request.getAmount());
        if(count <= 0){
            throw new RuntimeException("扣减冻结金额失败");
        }

//        int i = 10 / 0;

        log.info("account cancel 执行结束，txNo:{}",txNo);
    }

    private Account getAccount(AccountRequest request,String txNo){
        Account account = accountMapper.selectByPrimaryKey(request.getUserId());
        if (Objects.isNull(account)) {
            log.info("账户余额为空，txNo:{}",txNo);
            throw new RuntimeException("账户为空");
        }
        return account;
    }

}
