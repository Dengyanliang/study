package com.deng.study.dao;

import com.deng.study.dao.mapper.PayOrderMapper;
import com.deng.study.dao.po.PayOrder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:05
 */
@Component
public class OrderDaoImpl implements OrderDao{

    @Resource
    PayOrderMapper payOrderMapper;

    @Override
    public void insert(PayOrder payOrder) {
        payOrderMapper.insert(payOrder);
    }

    @Override
    public void batchInsert(List<PayOrder> orderList) {
        payOrderMapper.batchInsert(orderList);
    }
}
