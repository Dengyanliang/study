package com.deng.hmily.tcc.bank2.dao.po;

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