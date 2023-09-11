package com.deng.seata.order.tx.dao.po;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 购买数量
     */
    private Long count;

    /**
     * 支付金额
     */
    private Long payAmount;

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