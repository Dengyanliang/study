package com.deng.seata.account.tx.dao.mapper;

import com.deng.seata.account.tx.dao.po.Account;

public interface AccountMapper {

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

}