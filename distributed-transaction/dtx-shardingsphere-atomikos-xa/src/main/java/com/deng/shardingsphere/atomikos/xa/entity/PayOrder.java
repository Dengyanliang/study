package com.deng.shardingsphere.atomikos.xa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("pay_order")
public class PayOrder implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 订单金额，单位：分 (总金额)
     */
    private Long orderFee;

    /**
     * 支付状态，0-初始,1-处理中,2-成功,3-失败
     */
    private Integer payStatus;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单时间
     */
    private Date createTime;

    /**
     * 支付完成时间
     */
    private Date payFinishTime;
}