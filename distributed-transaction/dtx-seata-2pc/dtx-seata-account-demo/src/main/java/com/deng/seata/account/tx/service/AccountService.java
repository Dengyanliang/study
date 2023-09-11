package com.deng.seata.account.tx.service;

import com.deng.seata.account.tx.facade.request.AccountRequest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {
    void transfer(AccountRequest request);
}
