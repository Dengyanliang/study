package com.deng.shardingsphere.dynamic.datasource.service.impl;

import com.deng.shardingsphere.dynamic.datasource.annotation.MyDS;
import com.deng.shardingsphere.dynamic.datasource.entity.PayOrder;
import com.deng.shardingsphere.dynamic.datasource.mapper.PayOrderMapper;
import com.deng.shardingsphere.dynamic.datasource.service.PayOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    PayOrderMapper payOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @MyDS(name = "master")
    public void addOrder(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
//        int i = 10 / 0;
    }

    @Override
    @MyDS(name = "slave1")
    public PayOrder getOrder(Long id,Long productId) {
        return payOrderMapper.selectByPrimaryKey(id);
    }
}




