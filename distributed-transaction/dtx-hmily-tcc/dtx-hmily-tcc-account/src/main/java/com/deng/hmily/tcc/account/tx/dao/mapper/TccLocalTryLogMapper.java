package com.deng.hmily.tcc.account.tx.dao.mapper;

import com.deng.hmily.tcc.account.tx.dao.po.TccLocalTryLog;

public interface TccLocalTryLogMapper {

    int insert(TccLocalTryLog record);

    TccLocalTryLog selectByPrimaryKey(String txNo);
}