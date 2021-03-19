package com.deng.study.dao.mapper;

import com.deng.study.dao.po.PayOrder;
import com.deng.study.dao.po.PayOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayOrderMapper {
    long countByExample(PayOrderExample example);

    int deleteByExample(PayOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PayOrder record);

    int batchInsert(@Param("orderList") List<PayOrder> orderList);

    int insertSelective(PayOrder record);

    List<PayOrder> selectByExample(PayOrderExample example);

    PayOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PayOrder record, @Param("example") PayOrderExample example);

    int updateByExample(@Param("record") PayOrder record, @Param("example") PayOrderExample example);

    int updateByPrimaryKeySelective(PayOrder record);

    int updateByPrimaryKey(PayOrder record);
}