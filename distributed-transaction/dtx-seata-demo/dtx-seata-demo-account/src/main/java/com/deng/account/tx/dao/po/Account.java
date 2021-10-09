package com.deng.account.tx.dao.po;

import java.util.Date;

public class Account {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Double balance;

    /**
     * 
     */
    private Date lastUpdateTime;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return balance 
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance 
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * 
     * @return last_update_time 
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 
     * @param lastUpdateTime 
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}