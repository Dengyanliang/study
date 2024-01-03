package com.deng.study.dao;

import com.deng.study.dao.mapper.MyOrderMapper;
import com.deng.study.dao.po.MyOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:05
 */
@Component
public class MyOrderDaoImpl implements MyOrderDao{

    @Resource
    MyOrderMapper myOrderMapper;

    @Override
    public void addMyOrder(MyOrder MyOrder) {
        myOrderMapper.insert(MyOrder);
    }

    @Override
    public void addBatchMyOrderByThread(List<MyOrder> myOrderList) {

    }

    @Override
    public void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList) {

    }
}
