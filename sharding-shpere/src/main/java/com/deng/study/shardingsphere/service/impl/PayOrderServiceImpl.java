package com.deng.study.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.mapper.PayOrderMapper;
import com.deng.study.shardingsphere.dao.po.PayOrder;
import com.deng.study.shardingsphere.service.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
//    @DS("master0")
    public void addPayOrder(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
    }

    @Override
//    @DS("master0")
    public PayOrder getPayOrder(QueryWrapper<PayOrder> queryWrapper) {
        return payOrderMapper.selectOne(queryWrapper);
    }
}




