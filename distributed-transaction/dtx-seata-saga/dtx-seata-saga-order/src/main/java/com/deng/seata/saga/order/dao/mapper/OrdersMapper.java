package com.deng.seata.saga.order.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.seata.saga.order.dao.po.Orders;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}