package com.deng.study.mybatis.plus.dynamic.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deng.study.mybatis.plus.dynamic.datasource.entity.PayOrder;
import com.deng.study.mybatis.plus.dynamic.datasource.mapper.PayOrderMapper;
import com.deng.study.mybatis.plus.dynamic.datasource.service.PayOrderService;
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

    @DS("master")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
//        int i = 10 / 0;
    }

    @DS("slave")
    @Override
    public PayOrder getOrder(Long id,Long productId) {
        return payOrderMapper.selectByPrimaryKey(id);
    }
}




