package com.deng.seata.saga.account.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.seata.saga.account.dao.po.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}