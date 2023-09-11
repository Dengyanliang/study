package com.deng.hmily.tcc.account.tx.dao.mapper;

import com.deng.hmily.tcc.account.tx.dao.po.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {

    Account selectByPrimaryKey(Integer id);

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