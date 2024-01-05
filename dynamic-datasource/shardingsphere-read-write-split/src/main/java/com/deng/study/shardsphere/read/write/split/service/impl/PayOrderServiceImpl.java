package com.deng.study.shardsphere.read.write.split.service.impl;

import com.deng.study.shardsphere.read.write.split.entity.PayOrder;
import com.deng.study.shardsphere.read.write.split.mapper.PayOrderMapper;
import com.deng.study.shardsphere.read.write.split.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Slf4j
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    PayOrderMapper payOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
//        int i = 10 / 0;
    }

    @Override
    public PayOrder getOrder(Long id, Long productId) {
        return payOrderMapper.selectByPrimaryKey(id);
    }
}




