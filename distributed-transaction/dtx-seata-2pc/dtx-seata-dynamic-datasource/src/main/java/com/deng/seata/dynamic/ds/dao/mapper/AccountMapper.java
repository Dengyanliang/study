package com.deng.seata.dynamic.ds.dao.mapper;


import com.deng.seata.dynamic.ds.dao.po.Account;

public interface AccountMapper {

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

}