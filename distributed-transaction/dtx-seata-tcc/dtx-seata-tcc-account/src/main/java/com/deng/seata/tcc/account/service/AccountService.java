package com.deng.seata.tcc.account.service;


import com.deng.seata.tcc.account.facade.request.AccountRequest;
import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {
    void transfer(AccountRequest request);
    void transferCommit(AccountRequest request);
    void transferCancel(AccountRequest request);
}
