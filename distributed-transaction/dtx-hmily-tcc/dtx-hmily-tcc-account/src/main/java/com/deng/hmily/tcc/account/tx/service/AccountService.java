package com.deng.hmily.tcc.account.tx.service;


import com.deng.hmily.tcc.account.tx.facade.request.AccountRequest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {

    /**
     * 尝试冻结金额
     * @param request
     */
    void tryFreezeAmount(AccountRequest request);

}
