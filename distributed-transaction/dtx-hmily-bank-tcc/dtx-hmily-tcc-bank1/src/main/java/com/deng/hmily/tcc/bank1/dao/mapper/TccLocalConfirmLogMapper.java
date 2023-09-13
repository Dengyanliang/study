package com.deng.hmily.tcc.bank1.dao.mapper;

import com.deng.hmily.tcc.bank1.dao.po.TccLocalConfirmLog;

public interface TccLocalConfirmLogMapper {

    int insert(TccLocalConfirmLog record);

    TccLocalConfirmLog selectByPrimaryKey(String txNo);
}