package com.deng.tcc.account.tx.dao.po;

import java.util.Date;

public class TccLocalConfirmLog {
    /**
     * 事务id
     */
    private String txNo;

    /**
     * 
     */
    private Date createTime;

    /**
     * 事务id
     * @return tx_no 事务id
     */
    public String getTxNo() {
        return txNo;
    }

    /**
     * 事务id
     * @param txNo 事务id
     */
    public void setTxNo(String txNo) {
        this.txNo = txNo == null ? null : txNo.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}