package com.deng.restroom.service;

import com.deng.restroom.api.IRestroomService;
import com.deng.restroom.pojo.Toilet;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 19:34
 */
@LocalTCC // 该注解一定要加到接口上
public interface IRestroomTccService extends IRestroomService {


    @TwoPhaseBusinessAction(name = "releaseTCC",
            commitMethod = "releaseCommit",
            rollbackMethod = "releaseCancel")
    Toilet releaseTCC(@BusinessActionContextParameter(paramName = "id") Long id);

    public boolean releaseCommit(BusinessActionContext id);

    public boolean releaseCancel(BusinessActionContext id);
}
