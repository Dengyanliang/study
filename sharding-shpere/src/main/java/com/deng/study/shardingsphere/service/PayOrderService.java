package com.deng.study.shardingsphere.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.po.Course;
import com.deng.study.shardingsphere.po.PayOrder;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface PayOrderService {
    void addPayOrder(PayOrder payOrder);

    PayOrder getPayOrder(QueryWrapper<PayOrder> queryWrapper);
}
