package com.deng.study.dao;

import com.deng.study.dao.po.PayOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:05
 */
public interface OrderDao {
    void insert(PayOrder payOrder);
    void batchInsert(List<PayOrder> orderList);

    PayOrder select(Long id);
}
