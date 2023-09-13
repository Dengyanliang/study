package com.deng.hmily.tcc.bank2.service;


import com.deng.hmily.tcc.bank2.facade.request.Bank2AccountRequest;

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
    void tryFreezeAmount(Bank2AccountRequest request);

}
