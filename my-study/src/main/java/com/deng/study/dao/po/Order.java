package com.deng.study.dao.po;

import java.util.Date;

public class Order {
    /**
     * 订单流水号
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
     * 商品数量
     */
    private Long productNum;

    /**
     * 订单总价，单位分
     */
    private Long orderPrice;

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败
     */
    private Integer status;

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败，4-放弃支付，5-支付超时
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 支付完成时间
     */
    private Date payFinishedTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单流水号
     * @return id 订单流水号
     */
    public Long getId() {
        return id;
    }

    /**
     * 订单流水号
     * @param id 订单流水号
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
     * 商品数量
     * @return product_num 商品数量
     */
    public Long getProductNum() {
        return productNum;
    }

    /**
     * 商品数量
     * @param productNum 商品数量
     */
    public void setProductNum(Long productNum) {
        this.productNum = productNum;
    }

    /**
     * 订单总价，单位分
     * @return order_price 订单总价，单位分
     */
    public Long getOrderPrice() {
        return orderPrice;
    }

    /**
     * 订单总价，单位分
     * @param orderPrice 订单总价，单位分
     */
    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败
     * @return status 订单状态：0-初始化，1-支付中，2-成功，3-失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败
     * @param status 订单状态：0-初始化，1-支付中，2-成功，3-失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败，4-放弃支付，5-支付超时
     * @return pay_status 订单状态：0-初始化，1-支付中，2-成功，3-失败，4-放弃支付，5-支付超时
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 订单状态：0-初始化，1-支付中，2-成功，3-失败，4-放弃支付，5-支付超时
     * @param payStatus 订单状态：0-初始化，1-支付中，2-成功，3-失败，4-放弃支付，5-支付超时
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 支付完成时间
     * @return pay_finished_time 支付完成时间
     */
    public Date getPayFinishedTime() {
        return payFinishedTime;
    }

    /**
     * 支付完成时间
     * @param payFinishedTime 支付完成时间
     */
    public void setPayFinishedTime(Date payFinishedTime) {
        this.payFinishedTime = payFinishedTime;
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
}