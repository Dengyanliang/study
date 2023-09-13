package com.deng.hmily.tcc.bank2.dao.mapper;

import com.deng.hmily.tcc.bank2.dao.po.TccLocalTryLog;

public interface TccLocalTryLogMapper {

    int insert(TccLocalTryLog record);

    TccLocalTryLog selectByPrimaryKey(String txNo);
}