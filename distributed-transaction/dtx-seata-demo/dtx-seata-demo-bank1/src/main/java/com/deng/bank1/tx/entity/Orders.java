package com.deng.bank1.tx.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class Orders {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer productId;

    /**
     * 
     */
    private BigDecimal payAmount;

    /**
     * 
     */
    private Date addTime;

    /**
     * 
     */
    private Date lastUpdateTime;

}