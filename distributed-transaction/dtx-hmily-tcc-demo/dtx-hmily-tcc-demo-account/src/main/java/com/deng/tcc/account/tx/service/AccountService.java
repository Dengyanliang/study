package com.deng.tcc.account.tx.service;


import com.deng.tcc.account.tx.facade.request.AccountRequest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {
    void updateBalance(AccountRequest request);
}
