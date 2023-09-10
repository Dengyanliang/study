package com.deng.hmily.tcc.storage.tx.dao.po;

import java.util.Date;

public class Product {
    /**
     * 
     */
    private Long id;

    /**
     * 总库存
     */
    private Long stock;

    /**
     * 冻结库存
     */
    private Long freezeStock;

    /**
     * 
     */
    private Date lastUpdateTime;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 总库存
     * @return stock 总库存
     */
    public Long getStock() {
        return stock;
    }

    /**
     * 总库存
     * @param stock 总库存
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }

    /**
     * 冻结库存
     * @return freeze_stock 冻结库存
     */
    public Long getFreezeStock() {
        return freezeStock;
    }

    /**
     * 冻结库存
     * @param freezeStock 冻结库存
     */
    public void setFreezeStock(Long freezeStock) {
        this.freezeStock = freezeStock;
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