package com.deng.tcc.order.tx.dao.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Orders implements Serializable {
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
     * 支付状态，0-初始,1-处理中,2-成功,3-失败,4-关闭
     */
    private Integer payStatus;

    /**
     * 
     */
    private Date addTime;

    /**
     * 
     */
    private Date lastUpdateTime;

}