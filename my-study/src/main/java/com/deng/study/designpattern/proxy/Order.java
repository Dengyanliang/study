package com.deng.study.designpattern.proxy;

/**
 * @Desc:
 * @Date: 2023/12/29 19:46
 * @Auther: dengyanliang
 */
public class Order {
    private Object orderInfo;
    private Integer userId;

    public Object getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Object orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
