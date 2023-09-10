package com.deng.hmily.tcc.storage.tx.dao.po;

import lombok.Data;

import java.util.Date;

@Data
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

}