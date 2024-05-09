package com.deng.seata.tcc.order.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.seata.tcc.order.dao.po.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}