package com.deng.study.shardingsphere.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
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
     * 用户id
     * @return user_id 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 商品id
     * @return product_id 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 商品id
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 订单金额，单位：分 (总金额)
     * @return order_Fee 订单金额，单位：分 (总金额)
     */
    public Long getOrderFee() {
        return orderFee;
    }

    /**
     * 订单金额，单位：分 (总金额)
     * @param orderFee 订单金额，单位：分 (总金额)
     */
    public void setOrderFee(Long orderFee) {
        this.orderFee = orderFee;
    }

    /**
     * 支付状态，0-初始,1-处理中,2-成功,3-失败
     * @return pay_status 支付状态，0-初始,1-处理中,2-成功,3-失败
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 支付状态，0-初始,1-处理中,2-成功,3-失败
     * @param payStatus 支付状态，0-初始,1-处理中,2-成功,3-失败
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 版本号
     * @return version 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 版本号
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 订单时间
     * @return create_Time 订单时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 订单时间
     * @param createTime 订单时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 支付完成时间
     * @return pay_finish_time 支付完成时间
     */
    public Date getPayFinishTime() {
        return payFinishTime;
    }

    /**
     * 支付完成时间
     * @param payFinishTime 支付完成时间
     */
    public void setPayFinishTime(Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }
}