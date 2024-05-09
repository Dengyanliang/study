package com.deng.seata.tcc.account.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.seata.tcc.account.dao.po.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

//    Account selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(Account record);

}