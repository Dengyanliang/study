package com.deng.tcc.account.tx.dao.po;

import lombok.Data;

import java.util.Date;

@Data
public class TccLocalConfirmLog {
    /**
     * 事务id
     */
    private String txNo;

    /**
     * 
     */
    private Date createTime;
}