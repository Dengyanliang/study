package com.deng.hmily.tcc.account.tx.dao.mapper;

import com.deng.hmily.tcc.account.tx.dao.po.TccLocalCancelLog;

public interface TccLocalCancelLogMapper {

    int insert(TccLocalCancelLog record);

    TccLocalCancelLog selectByPrimaryKey(String txNo);
}