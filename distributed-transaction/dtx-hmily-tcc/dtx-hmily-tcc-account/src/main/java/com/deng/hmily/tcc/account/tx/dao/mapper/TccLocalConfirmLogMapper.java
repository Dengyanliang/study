package com.deng.hmily.tcc.account.tx.dao.mapper;

import com.deng.hmily.tcc.account.tx.dao.po.TccLocalConfirmLog;

public interface TccLocalConfirmLogMapper {

    int insert(TccLocalConfirmLog record);

    TccLocalConfirmLog selectByPrimaryKey(String txNo);
}