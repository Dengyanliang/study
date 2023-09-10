package com.deng.hmily.tcc.order.tx.dao.mapper;

import com.deng.hmily.tcc.order.tx.dao.po.Orders;
import com.deng.hmily.tcc.order.tx.dao.po.OrdersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrdersMapper {
    long countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    Orders selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}