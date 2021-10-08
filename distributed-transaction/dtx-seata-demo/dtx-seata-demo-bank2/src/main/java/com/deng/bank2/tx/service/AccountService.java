package com.deng.bank2.tx.service;

import com.deng.bank2.tx.facade.request.AccountRequest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {
    boolean transfer(AccountRequest request);
}
