package com.deng.hmily.tcc.bank1.service.impl;

import com.deng.hmily.tcc.bank1.dao.mapper.AccountMapper;
import com.deng.hmily.tcc.bank1.dao.mapper.TccLocalCancelLogMapper;
import com.deng.hmily.tcc.bank1.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.hmily.tcc.bank1.dao.mapper.TccLocalTryLogMapper;
import com.deng.hmily.tcc.bank1.dao.po.Account;
import com.deng.hmily.tcc.bank1.dao.po.TccLocalCancelLog;
import com.deng.hmily.tcc.bank1.dao.po.TccLocalTryLog;
import com.deng.hmily.tcc.bank1.facade.request.Bank1AccountRequest;
import com.deng.hmily.tcc.bank1.remote.client.AccountClient;
import com.deng.hmily.tcc.bank1.remote.request.Bank2AccountRequest;
import com.deng.hmily.tcc.bank1.remote.response.Bank2AccountResponse;
import com.deng.hmily.tcc.bank1.service.AccountService;
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
    private AccountMapper bank1AccountMapper;

    @Resource
    private TccLocalTryLogMapper tccLocalTryLogMapper;

    @Resource
    private TccLocalConfirmLogMapper tccLocalConfirmLogMapper;

    @Resource
    private TccLocalCancelLogMapper tccLocalCancelLogMapper;

    @Resource
    private AccountClient bank2AccountClient;

    @Override
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    @Transactional(rollbackFor = Exception.class)
    public void tryFreezeAmount(Bank1AccountRequest request) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account try 开始执行，txNo:{}",txNo);

        if(Objects.nonNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("account try 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行  todo 这里应该只用判断cancel的吧？
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("account try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
            return;
        }

        // 添加try日志
        TccLocalTryLog tryLog = new TccLocalTryLog();
        tryLog.setTxNo(txNo);
        tryLog.setCreateTime(new Date());
        tccLocalTryLogMapper.insert(tryLog);

        Account dbAccount = getAccount(request,txNo);

        // try 账户余额=账户余额-amount
        int count = bank1AccountMapper.increaseAccountAmountById(dbAccount.getId(),request.getAmount()*-1);
        if(count <= 0){
            throw new RuntimeException("增加冻结金额失败");
        }

        Bank2AccountRequest bank2AccountRequest = new Bank2AccountRequest();
        bank2AccountRequest.setUserId(request.getBank2UserId());
        bank2AccountRequest.setAmount(request.getAmount());

        Bank2AccountResponse bank2AccountResponse = bank2AccountClient.tryFreezeAmount(bank2AccountRequest);

        int i = 10 / 0;


        log.info("account try 执行结束，txNo:{}",txNo);
    }

    public void commit(Bank1AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account confirm 开始执行，txNo:{}",txNo);

        log.info("account confirm 执行结束，txNo:{}",txNo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void rollback(Bank1AccountRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("account cancel 开始执行，txNo:{}",txNo);

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("account 空回滚处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
            return;
        }

        // cancel幂等校验
        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("account cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        TccLocalCancelLog cancelLog = new TccLocalCancelLog();
        cancelLog.setTxNo(txNo);
        cancelLog.setCreateTime(new Date());
        tccLocalCancelLogMapper.insert(cancelLog);

        Account dbAccount = getAccount(request,txNo);

        // cancel  账户余额=账户余额+amount
        int count = bank1AccountMapper.increaseAccountAmountById(dbAccount.getId(),request.getAmount());
        if(count <= 0){
            throw new RuntimeException("扣减冻结金额失败");
        }

//        int i = 10 / 0;

        log.info("account cancel 执行结束，txNo:{}",txNo);
    }

    private Account getAccount(Bank1AccountRequest request, String txNo){
        Account account = bank1AccountMapper.selectByPrimaryKey(request.getBank1userId());
        if (Objects.isNull(account)) {
            log.info("账户余额为空，txNo:{}",txNo);
            throw new RuntimeException("账户为空");
        }
        return account;
    }

}
