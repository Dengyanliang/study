package com.deng.hmily.tcc.bank2.dao.mapper;

import com.deng.hmily.tcc.bank2.dao.po.TccLocalCancelLog;

public interface TccLocalCancelLogMapper {

    int insert(TccLocalCancelLog record);

    TccLocalCancelLog selectByPrimaryKey(String txNo);
}