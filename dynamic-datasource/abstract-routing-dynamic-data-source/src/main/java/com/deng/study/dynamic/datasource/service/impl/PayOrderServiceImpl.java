package com.deng.study.dynamic.datasource.service.impl;

import com.deng.study.dynamic.datasource.annotation.MyDS;
import com.deng.study.dynamic.datasource.entity.PayOrder;
import com.deng.study.dynamic.datasource.mapper.PayOrderMapper;
import com.deng.study.dynamic.datasource.service.PayOrderService;
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
    @MyDS(isMaster = true)
    public void addOrder(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
        int i = 10 / 0;
    }


    @Override
    @MyDS(isMaster = true)
    public PayOrder getOrder(Long id,Long productId) {
        return payOrderMapper.selectByPrimaryKey(id);
//        return payOrderMapper.selectByPrimaryKeyAndProductId(id,productId);
    }
}




