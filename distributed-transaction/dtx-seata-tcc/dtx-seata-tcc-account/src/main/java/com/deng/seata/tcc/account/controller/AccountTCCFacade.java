package com.deng.seata.tcc.account.controller;

import com.deng.seata.tcc.account.facade.request.AccountRequest;
import com.deng.seata.tcc.account.facade.response.AccountResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Desc:
 * @Date: 2024/5/8 17:07
 * @Auther: dengyanliang
 */
@LocalTCC // 这个注解要加上，否则控制不了事务，即收不到transferCommit和transferCancel的请求信息
public interface AccountTCCFacade {

    @TwoPhaseBusinessAction(name = "transferTCC",commitMethod = "transferCommit", rollbackMethod = "transferCancel")
    AccountResponse transferTCC(@BusinessActionContextParameter(paramName = "accountRequest")
                                @RequestBody AccountRequest request);
    void transferCommit(BusinessActionContext actionContext);
    void transferCancel(BusinessActionContext actionContext);
}
