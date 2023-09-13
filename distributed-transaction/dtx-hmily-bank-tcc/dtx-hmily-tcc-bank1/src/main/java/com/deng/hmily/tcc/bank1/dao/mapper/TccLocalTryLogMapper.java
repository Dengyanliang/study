package com.deng.hmily.tcc.bank1.dao.mapper;

import com.deng.hmily.tcc.bank1.dao.po.TccLocalTryLog;

public interface TccLocalTryLogMapper {

    int insert(TccLocalTryLog record);

    TccLocalTryLog selectByPrimaryKey(String txNo);
}