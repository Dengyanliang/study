package com.deng.hmily.tcc.account.tx.dao.mapper;

import com.deng.hmily.tcc.account.tx.dao.po.Account;
import com.deng.hmily.tcc.account.tx.dao.po.AccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    /**
     * 增加冻结金额 --- try调用
     * @param id
     * @param amount
     * @return
     */
    int addFreezeAmountAndCheckBalanceById(@Param("id") long id,@Param("amount") long amount);


    /**
     * 扣减冻结金额、扣减余额 --- confirm调用
     * @param id
     * @param amount
     * @return
     */
    int deductBalanceAndFreezeAmountById(@Param("id") long id, @Param("amount") long amount);

    /**
     * 扣减冻结金额 --- cancel调用
     * @param id
     * @param amount
     * @return
     */
    int deductFreezeAmountById(@Param("id") long id, @Param("amount") long amount);
}