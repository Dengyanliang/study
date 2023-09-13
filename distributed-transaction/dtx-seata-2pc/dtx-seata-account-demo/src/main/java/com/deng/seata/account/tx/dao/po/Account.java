package com.deng.seata.account.tx.dao.po;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户余额，单位分
     */
    private Long balance;

    /**
     * 冻结金额，扣款暂存余额，单位分
     */
    private Long freezeAmount;

    /**
     * 
     */
    private Date lastUpdateTime;

}