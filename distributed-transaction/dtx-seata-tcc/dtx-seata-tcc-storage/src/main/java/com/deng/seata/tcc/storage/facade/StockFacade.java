package com.deng.seata.tcc.storage.facade;


import com.deng.seata.tcc.storage.facade.request.StockRequest;
import com.deng.seata.tcc.storage.facade.response.StockResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Desc:
 * @Date: 2024/5/17 16:36
 * @Auther: dengyanliang
 */
@LocalTCC
public interface StockFacade {

    @TwoPhaseBusinessAction(name = "tryFreezeStock",commitMethod = "commitFreezeStock", rollbackMethod = "cancelFreezeStock")
    StockResponse tryFreezeStock(@BusinessActionContextParameter(paramName = "stockRequest") StockRequest stockRequest);

    void commitFreezeStock(BusinessActionContext actionContext);

    void cancelFreezeStock(BusinessActionContext actionContext);
}
