package com.deng.hmily.tcc.bank1.service;


import com.deng.hmily.tcc.bank1.facade.request.Bank1AccountRequest;

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
    void tryFreezeAmount(Bank1AccountRequest request);

}
